import sys
import subprocess
import os
import requests
import zipfile
from tqdm import tqdm
from PIL import Image
import pandas as pd


def install_requirements():
    required_libraries = ['requests', 'tqdm', 'Pillow', 'pandas']
    for library in required_libraries:
        subprocess.check_call([sys.executable, "-m", "pip", "install", library])


try:
    import requests
    from tqdm import tqdm
    from PIL import Image
    import pandas as pd
except ImportError:
    print("Some required libraries are missing. Installing them now...")
    install_requirements()
    import requests
    from tqdm import tqdm
    from PIL import Image
    import pandas as pd

# URLs for Flickr8k dataset
IMAGES_URL = "https://github.com/jbrownlee/Datasets/releases/download/Flickr8k/Flickr8k_Dataset.zip"
CAPTIONS_URL = "https://github.com/jbrownlee/Datasets/releases/download/Flickr8k/Flickr8k_text.zip"


def download_file(url, filename):
    response = requests.get(url, stream=True)
    total_size = int(response.headers.get('content-length', 0))
    block_size = 1024  # 1 KB

    with open(filename, 'wb') as file, tqdm(
            desc=filename,
            total=total_size,
            unit='iB',
            unit_scale=True,
            unit_divisor=1024,
    ) as progress_bar:
        for data in response.iter_content(block_size):
            size = file.write(data)
            progress_bar.update(size)


def extract_zip(filename, extract_path):
    with zipfile.ZipFile(filename, 'r') as zip_ref:
        zip_ref.extractall(extract_path)


def process_captions(captions_file):
    captions = {}
    with open(captions_file, 'r') as f:
        for line in f:
            parts = line.strip().split('\t')
            if len(parts) == 2:
                image_name = parts[0].split('#')[0]  # Remove the #0, #1, etc.
                caption = parts[1]
                if image_name not in captions:
                    captions[image_name] = []
                captions[image_name].append(caption)
    return captions


def main():
    # Create directories
    os.makedirs('flickr8k', exist_ok=True)
    os.makedirs('flickr8k/images', exist_ok=True)

    # Download and extract images
    images_zip = 'flickr8k/Flickr8k_Dataset.zip'
    if not os.path.exists(images_zip):
        print("Downloading images...")
        download_file(IMAGES_URL, images_zip)
    print("Extracting images...")
    extract_zip(images_zip, 'flickr8k/images')

    # Download and extract captions
    captions_zip = 'flickr8k/Flickr8k_text.zip'
    if not os.path.exists(captions_zip):
        print("Downloading captions...")
        download_file(CAPTIONS_URL, captions_zip)
    print("Extracting captions...")
    extract_zip(captions_zip, 'flickr8k')

    # Process captions
    captions = process_captions('flickr8k/Flickr8k.token.txt')

    # Create a DataFrame with image paths and captions
    data = []
    for image_name, image_captions in captions.items():
        image_path = os.path.join("flickr8k", "images", "Flicker8k_Dataset", image_name)
        for caption in image_captions:
            data.append({'image_path': image_path, 'caption': caption})

    df = pd.DataFrame(data)
    df.to_csv('flickr8k/flickr8k_data.csv', index=False)
    print(f"Processed {len(df)} image-caption pairs")

    # Display a sample
    sample = df.sample(1).iloc[0]
    print("\nSample:")
    print(f"Image: {sample['image_path']}")
    print(f"Caption: {sample['caption']}")

    img_path = sample['image_path']
    if os.path.exists(img_path):
        img = Image.open(img_path)
        img.show()
    else:
        print(f"Image file not found: {img_path}")


if __name__ == "__main__":
    main()