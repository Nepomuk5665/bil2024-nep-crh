import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import DataLoader, TensorDataset
from torchvision import transforms
import os
from PIL import Image
import json
import time
import multiprocessing
from torch.multiprocessing import Pool, set_start_method
import numpy as np
from tqdm import tqdm

# Set the start method for multiprocessing
try:
    set_start_method('spawn')
except RuntimeError:
    pass


class ImageClassifier(nn.Module):
    def __init__(self, num_classes):
        super(ImageClassifier, self).__init__()
        self.model = nn.Sequential(
            nn.Conv2d(3, 64, 4, 2, 1),
            nn.ReLU(inplace=True),
            nn.Conv2d(64, 128, 4, 2, 1),
            nn.BatchNorm2d(128),
            nn.ReLU(inplace=True),
            nn.Conv2d(128, 256, 4, 2, 1),
            nn.BatchNorm2d(256),
            nn.ReLU(inplace=True),
            nn.Conv2d(256, 512, 4, 2, 1),
            nn.BatchNorm2d(512),
            nn.ReLU(inplace=True),
            nn.AdaptiveAvgPool2d((1, 1)),
            nn.Flatten(),
            nn.Linear(512, num_classes)
        )

    def forward(self, img):
        return self.model(img)


def weights_init(m):
    classname = m.__class__.__name__
    if classname.find('Conv') != -1:
        nn.init.normal_(m.weight.data, 0.0, 0.02)
    elif classname.find('BatchNorm') != -1:
        nn.init.normal_(m.weight.data, 1.0, 0.02)
        nn.init.constant_(m.bias.data, 0)


def train(dataloader, num_epochs, num_classes, device):
    model = ImageClassifier(num_classes).to(device)
    model.apply(weights_init)
    print(f"Model initialized with {sum(p.numel() for p in model.parameters())} parameters")

    optimizer = optim.Adam(model.parameters(), lr=0.0002, betas=(0.5, 0.999))
    scheduler = optim.lr_scheduler.StepLR(optimizer, step_size=30, gamma=0.1)
    criterion = nn.CrossEntropyLoss()

    print(f"Starting training for {num_epochs} epochs")
    start_time = time.time()
    for epoch in range(num_epochs):
        epoch_start_time = time.time()
        epoch_loss = 0.0
        correct_predictions = 0
        total_predictions = 0

        progress_bar = tqdm(dataloader, desc=f"Epoch {epoch + 1}/{num_epochs}")
        for i, (images, labels) in enumerate(progress_bar):
            images = images.to(device, non_blocking=True)
            labels = labels.to(device, non_blocking=True)

            optimizer.zero_grad(set_to_none=True)
            outputs = model(images)
            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()

            epoch_loss += loss.item()
            _, predicted = torch.max(outputs.data, 1)
            total_predictions += labels.size(0)
            correct_predictions += (predicted == labels).sum().item()

            progress_bar.set_postfix({
                'Loss': f"{loss.item():.4f}",
                'Accuracy': f"{100 * correct_predictions / total_predictions:.2f}%"
            })

        epoch_time = time.time() - epoch_start_time
        epoch_loss /= len(dataloader)
        epoch_accuracy = 100 * correct_predictions / total_predictions
        print(f"Epoch {epoch + 1} completed in {epoch_time:.2f} seconds")
        print(f"Epoch {epoch + 1} average loss: {epoch_loss:.4f}")
        print(f"Epoch {epoch + 1} accuracy: {epoch_accuracy:.2f}%")

        scheduler.step()
        print(f"Learning rate adjusted to: {scheduler.get_last_lr()[0]:.6f}")

    total_time = time.time() - start_time
    print(f"Training completed in {total_time:.2f} seconds")

    return model


def process_image(args):
    img_path, label, transform = args
    try:
        img = Image.open(img_path).convert("RGB")
        if transform:
            img = transform(img)
        return img, label
    except Exception as e:
        print(f"Error processing image {img_path}: {str(e)}")
        return None, None


def main():
    print("Starting main function")

    # Hyperparameters
    batch_size = 256
    num_epochs = 20
    image_size = 64

    # Device configuration
    if torch.backends.mps.is_available():
        device = torch.device("mps")
        print("Using MPS (Metal) device")
    elif torch.cuda.is_available():
        device = torch.device("cuda")
        print("Using CUDA device")
    else:
        device = torch.device("cpu")
        print("Using CPU")

    # Load dataset
    data_dir = '/Users/nepocute/PycharmProjects/ai image generate/data/images/train/train2017'
    ann_file = '/Users/nepocute/PycharmProjects/ai image generate/data/annotations/stuff_train2017.json'

    print(f"Loading annotations from {ann_file}")
    print(f"Image directory: {data_dir}")

    transform = transforms.Compose([
        transforms.Resize(image_size),
        transforms.CenterCrop(image_size),
        transforms.ToTensor(),
        transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5)),
    ])

    # Load annotations and create class mapping
    with open(ann_file, 'r') as f:
        coco = json.load(f)

    categories = {cat['id']: cat['name'] for cat in coco['categories']}
    class_to_idx = {name: idx for idx, name in enumerate(sorted(set(categories.values())))}
    num_classes = len(class_to_idx)

    print(f"Number of classes: {num_classes}")
    print("Class mapping:")
    for class_name, idx in class_to_idx.items():
        print(f"  {class_name}: {idx}")

    # Prepare image paths and labels
    print("Preparing image data")
    image_data = []
    for ann in tqdm(coco['annotations'], desc="Processing annotations"):
        img_info = next(img for img in coco['images'] if img['id'] == ann['image_id'])
        image_data.append((
            os.path.join(data_dir, img_info['file_name']),
            class_to_idx[categories[ann['category_id']]]
        ))

    print(f"Total images: {len(image_data)}")

    # Prepare arguments for multiprocessing
    args_list = [(img_path, label, transform) for img_path, label in image_data]

    # Use 24 processes for image preprocessing
    num_processes = 24
    print(f"Using {num_processes} processes for image preprocessing")

    with Pool(num_processes) as p:
        processed_data = list(tqdm(p.imap(process_image, args_list), total=len(args_list), desc="Processing images"))

    # Remove any None values (failed processing)
    processed_data = [item for item in processed_data if item[0] is not None]

    print(f"Successfully processed {len(processed_data)} images")

    # Separate images and labels
    images, labels = zip(*processed_data)

    # Convert processed images to a tensor dataset
    tensor_dataset = TensorDataset(torch.stack(images), torch.tensor(labels))

    print(f"Creating DataLoader with batch size {batch_size}")
    dataloader = DataLoader(tensor_dataset, batch_size=batch_size, shuffle=True, num_workers=num_processes,
                            pin_memory=True)

    # Train the model
    print("Starting model training")
    model = train(dataloader, num_epochs, num_classes, device)

    # Save the trained model
    model_save_path = "image_classifier_model.pth"
    torch.save(model.state_dict(), model_save_path)
    print(f"Model saved successfully to {model_save_path}")


if __name__ == '__main__':
    main()