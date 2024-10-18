import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
from torchvision import transforms
from PIL import Image
import pandas as pd
import clip
from tqdm import tqdm
import os
import multiprocessing

# Check for MPS availability
if torch.backends.mps.is_available():
    device = torch.device("mps")
    print("Using MPS device")
    num_workers = 0
elif torch.cuda.is_available():
    device = torch.device("cuda")
    print("Using CUDA device")
    num_workers = multiprocessing.cpu_count()
else:
    device = torch.device("cpu")
    print("Using CPU device")
    num_workers = multiprocessing.cpu_count()

print(f"Number of workers: {num_workers}")


class Flickr8kDataset(Dataset):
    def __init__(self, csv_file, transform=None, root_dir=''):
        self.data = pd.read_csv(csv_file)
        self.transform = transform
        self.root_dir = root_dir
        self.clip_model, self.preprocess = clip.load("ViT-B/32", device="cpu")

        # Validate and filter the dataset
        self.validate_dataset()

    def validate_dataset(self):
        valid_data = []
        for idx, row in tqdm(self.data.iterrows(), total=len(self.data), desc="Validating dataset"):
            img_path = os.path.join(self.root_dir, row['image_path'])
            if os.path.exists(img_path):
                valid_data.append(row)
            else:
                print(f"Warning: Image not found - {img_path}")

        self.data = pd.DataFrame(valid_data)
        print(f"Valid images: {len(self.data)} out of {len(self.data)}")

    def __len__(self):
        return len(self.data)

    def __getitem__(self, idx):
        img_path = os.path.join(self.root_dir, self.data.iloc[idx]['image_path'])
        caption = self.data.iloc[idx]['caption']

        try:
            image = Image.open(img_path).convert('RGB')
        except Exception as e:
            print(f"Error loading image {img_path}: {str(e)}")
            image = Image.new('RGB', (128, 128), color='black')

        if self.transform:
            image = self.transform(image)

        text = clip.tokenize([caption]).squeeze(0)
        return image.float(), text


class ImageGenerator(nn.Module):
    def __init__(self):
        super(ImageGenerator, self).__init__()
        self.model = nn.Sequential(
            nn.ConvTranspose2d(512, 512, kernel_size=4, stride=1, padding=0),
            nn.BatchNorm2d(512),
            nn.ReLU(),
            nn.ConvTranspose2d(512, 256, kernel_size=4, stride=2, padding=1),
            nn.BatchNorm2d(256),
            nn.ReLU(),
            nn.ConvTranspose2d(256, 128, kernel_size=4, stride=2, padding=1),
            nn.BatchNorm2d(128),
            nn.ReLU(),
            nn.ConvTranspose2d(128, 64, kernel_size=4, stride=2, padding=1),
            nn.BatchNorm2d(64),
            nn.ReLU(),
            nn.ConvTranspose2d(64, 3, kernel_size=4, stride=2, padding=1),
            nn.Tanh()
        )

    def forward(self, x):
        return self.model(x.view(-1, 512, 1, 1))


def train(dataloader, generator, clip_model, optimizer, criterion, device, scaler):
    generator.train()
    total_loss = 0
    for images, texts in tqdm(dataloader, desc="Training"):
        images, texts = images.to(device).float(), texts.to(device)

        optimizer.zero_grad()

        with torch.no_grad():
            text_features = clip_model.encode_text(texts).float()

        with torch.autocast(device.type if device.type != 'mps' else 'cpu'):
            generated_images = generator(text_features)
            loss = criterion(generated_images, images)

        if device.type == 'cuda':
            scaler.scale(loss).backward()
            scaler.step(optimizer)
            scaler.update()
        else:
            loss.backward()
            optimizer.step()

        total_loss += loss.item()

    return total_loss / len(dataloader)


def main():
    print(f"Using device: {device}")

    # Hyperparameters
    batch_size = 256
    learning_rate = 0.0002
    num_epochs = 200

    # Load the dataset
    transform = transforms.Compose([
        transforms.Resize((64, 64)),  # Changed back to 64x64
        transforms.ToTensor(),
        transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))
    ])

    # Adjust the root_dir to match your project structure
    root_dir = '/Users/nepocute/PycharmProjects/generateaiimages'
    dataset = Flickr8kDataset('flickr8k/flickr8k_data.csv', transform=transform, root_dir=root_dir)

    # DataLoader configuration
    dataloader_kwargs = {
        'batch_size': batch_size,
        'shuffle': True,
        'num_workers': num_workers,
        'pin_memory': True
    }
    if num_workers > 0:
        dataloader_kwargs['prefetch_factor'] = 2

    dataloader = DataLoader(dataset, **dataloader_kwargs)

    # Initialize models
    generator = ImageGenerator().to(device).float()
    clip_model, _ = clip.load("ViT-B/32", device=device)

    # Loss and optimizer
    criterion = nn.MSELoss()
    optimizer = optim.Adam(generator.parameters(), lr=learning_rate)

    # Initialize the Gradient Scaler for mixed precision training
    scaler = torch.cuda.amp.GradScaler(enabled=(device.type == 'cuda'))

    # Training loop
    for epoch in range(num_epochs):
        loss = train(dataloader, generator, clip_model, optimizer, criterion, device, scaler)
        print(f"Epoch [{epoch + 1}/{num_epochs}], Loss: {loss:.4f}")

        # Save a sample generated image
        if (epoch + 1) % 10 == 0:
            generator.eval()
            with torch.no_grad():
                sample_text = clip.tokenize(["A dog running in a field"]).to(device)
                sample_text_features = clip_model.encode_text(sample_text).float()
                sample_image = generator(sample_text_features)
                sample_image = sample_image.squeeze(0).permute(1, 2, 0).cpu().numpy()
                sample_image = (sample_image + 1) / 2  # Denormalize
                sample_image = (sample_image * 255).astype('uint8')
                Image.fromarray(sample_image).save(f'sample_epoch_{epoch + 1}.png')

    # Save the trained model
    torch.save(generator.state_dict(), 'text_to_image_model.pth')


if __name__ == "__main__":
    multiprocessing.set_start_method('spawn', force=True)
    main()