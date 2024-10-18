import os
import pandas as pd
from PIL import Image
import requests
from io import BytesIO
from tqdm.auto import tqdm
from datasets import load_dataset
import time
from huggingface_hub import HfFolder
import logging
import random

# Set up logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')


def get_hf_token():
    token = os.environ.get("HUGGINGFACE_TOKEN")
    if not token:
        print("HUGGINGFACE_TOKEN environment variable not found.")
        token = input("Please enter your Hugging Face API token: ").strip()
    return token


def process_sample(sample, output_dir, max_retries=3):
    url = sample.get('URL', '')
    caption = sample.get('TEXT', '')

    if not url:
        return None, ("No URL", "URL not found in sample")

    for attempt in range(max_retries):
        try:
            response = requests.get(url, timeout=30)
            response.raise_for_status()
            img = Image.open(BytesIO(response.content)).convert('RGB')

            img_filename = f"image_{time.time()}_{random.randint(0, 1000000)}.jpg"
            img_path = os.path.join(output_dir, 'images', img_filename)
            img.save(img_path)

            return {'image_path': img_path, 'caption': caption}, None
        except requests.exceptions.RequestException as e:
            logging.warning(f"Attempt {attempt + 1} failed for URL {url}: {str(e)}")
            if attempt == max_retries - 1:
                return None, (url, str(e))
            time.sleep(1)  # Wait for 1 second before retrying
        except Exception as e:
            logging.error(f"Unexpected error processing URL {url}: {str(e)}")
            return None, (url, str(e))


def download_and_process_dataset(num_samples=1000000):
    output_dir = 'laion_dataset'
    os.makedirs(output_dir, exist_ok=True)
    os.makedirs(os.path.join(output_dir, 'images'), exist_ok=True)

    hf_token = get_hf_token()
    HfFolder.save_token(hf_token)  # This will set the token for the current session

    logging.info("Loading dataset...")
    dataset = load_dataset("laion/laion2B-en", split="train", streaming=True)

    all_data = []
    failed_downloads = []

    with tqdm(total=num_samples, desc="Processing samples") as pbar:
        for i, sample in enumerate(dataset):
            if i >= num_samples:
                break

            if i % 1000 == 0:
                logging.info(f"Processing sample {i}")

            data, error = process_sample(sample, output_dir)
            if data:
                all_data.append(data)
            else:
                failed_downloads.append(error)

            pbar.update(1)
            pbar.set_postfix({
                "Successful": len(all_data),
                "Failed": len(failed_downloads),
                "Success Rate": f"{len(all_data) / (i + 1):.2%}"
            })

            # Log a sample of successful and failed downloads
            if i % 10000 == 0:
                logging.info(f"Sample successful download: {all_data[-1] if all_data else 'None'}")
                logging.info(f"Sample failed download: {failed_downloads[-1] if failed_downloads else 'None'}")

    result_df = pd.DataFrame(all_data)
    result_df.to_csv(os.path.join(output_dir, 'laion_data.csv'), index=False)
    logging.info(f"\nProcessed {len(result_df):,} image-caption pairs")

    with open(os.path.join(output_dir, 'failed_downloads.txt'), 'w') as f:
        for url, error in failed_downloads:
            f.write(f"{url}: {error}\n")
    logging.info(f"Total successful downloads: {len(result_df):,}")
    logging.info(f"Total failed downloads: {len(failed_downloads):,}")
    logging.info("Failed download URLs and errors saved to 'laion_dataset/failed_downloads.txt'")

    # Log some failed download examples
    logging.info("Sample of failed downloads:")
    for url, error in failed_downloads[:10]:
        logging.info(f"{url}: {error}")


if __name__ == "__main__":
    download_and_process_dataset(num_samples=1000000)  # Process 1 million samples