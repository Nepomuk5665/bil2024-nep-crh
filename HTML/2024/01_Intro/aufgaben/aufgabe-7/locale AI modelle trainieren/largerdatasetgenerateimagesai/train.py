import os
import pandas as pd
from PIL import Image
import requests
from io import BytesIO
from tqdm.auto import tqdm
import time
import pyarrow.parquet as pq
from huggingface_hub import hf_hub_download


def process_chunk(chunk, output_dir):
    data = []
    failed_downloads = []

    for _, row in chunk.iterrows():
        url = row.get('URL', '')
        caption = row.get('TEXT', '')

        if not url:
            failed_downloads.append(("No URL", "URL not found in sample"))
            continue

        try:
            response = requests.get(url, timeout=10)
            response.raise_for_status()
            img = Image.open(BytesIO(response.content)).convert('RGB')

            img_filename = f"image_{len(data)}.jpg"
            img_path = os.path.join(output_dir, 'images', img_filename)
            img.save(img_path)

            data.append({
                'image_path': img_path,
                'caption': caption
            })
        except Exception as e:
            failed_downloads.append((url, str(e)))

    return data, failed_downloads


def download_and_process_dataset(num_samples=1000000):
    output_dir = 'laion_dataset'
    os.makedirs(output_dir, exist_ok=True)
    os.makedirs(os.path.join(output_dir, 'images'), exist_ok=True)

    # You need to set your Hugging Face API token as an environment variable
    # export HUGGINGFACE_TOKEN=your_token_here
    hf_token = os.environ.get("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")
    if not hf_token:
        raise ValueError("Please set the HUGGINGFACE_TOKEN environment variable")

    print("Downloading dataset chunk...")
    local_file = hf_hub_download(
        repo_id="laion/relaion2B-en-research-safe",
        filename="part-00000-339dc23d-0869-4dc0-9390-b4036fcc80c2-c000.snappy.parquet",
        token=hf_token
    )

    print("Processing downloaded chunk...")
    table = pq.read_table(local_file)
    df = table.to_pandas()

    total_samples = min(len(df), num_samples)
    df = df.sample(n=total_samples, random_state=42)

    chunk_size = 1000
    num_chunks = (total_samples + chunk_size - 1) // chunk_size

    all_data = []
    all_failed_downloads = []

    for i in tqdm(range(num_chunks), desc="Processing chunks"):
        start = i * chunk_size
        end = min((i + 1) * chunk_size, total_samples)
        chunk = df.iloc[start:end]

        data, failed_downloads = process_chunk(chunk, output_dir)
        all_data.extend(data)
        all_failed_downloads.extend(failed_downloads)

    result_df = pd.DataFrame(all_data)
    result_df.to_csv(os.path.join(output_dir, 'laion_data.csv'), index=False)
    print(f"\nProcessed {len(result_df):,} image-caption pairs")

    with open(os.path.join(output_dir, 'failed_downloads.txt'), 'w') as f:
        for url, error in all_failed_downloads:
            f.write(f"{url}: {error}\n")
    print(f"Total successful downloads: {len(result_df):,}")
    print(f"Total failed downloads: {len(all_failed_downloads):,}")
    print("Failed download URLs and errors saved to 'laion_dataset/failed_downloads.txt'")


if __name__ == "__main__":
    download_and_process_dataset(num_samples=1000000)  # Process 1 million samples