import torch
from torchvision import transforms
from PIL import Image
import json


class ImageClassifier(torch.nn.Module):
    def __init__(self, num_classes):
        super(ImageClassifier, self).__init__()
        self.model = torch.nn.Sequential(
            torch.nn.Conv2d(3, 64, 4, 2, 1),
            torch.nn.ReLU(inplace=True),
            torch.nn.Conv2d(64, 128, 4, 2, 1),
            torch.nn.BatchNorm2d(128),
            torch.nn.ReLU(inplace=True),
            torch.nn.Conv2d(128, 256, 4, 2, 1),
            torch.nn.BatchNorm2d(256),
            torch.nn.ReLU(inplace=True),
            torch.nn.Conv2d(256, 512, 4, 2, 1),
            torch.nn.BatchNorm2d(512),
            torch.nn.ReLU(inplace=True),
            torch.nn.AdaptiveAvgPool2d((1, 1)),
            torch.nn.Flatten(),
            torch.nn.Linear(512, num_classes)
        )

    def forward(self, img):
        return self.model(img)


def load_class_mapping(ann_file):
    with open(ann_file, 'r') as f:
        coco = json.load(f)

    categories = {cat['id']: cat['name'] for cat in coco['categories']}
    class_to_idx = {name: idx for idx, name in enumerate(sorted(set(categories.values())))}
    idx_to_class = {idx: name for name, idx in class_to_idx.items()}

    return idx_to_class


def load_model(model_path, num_classes, device):
    model = ImageClassifier(num_classes).to(device)
    model.load_state_dict(torch.load(model_path, map_location=device))
    model.eval()
    return model


def preprocess_image(image_path, image_size=64):
    transform = transforms.Compose([
        transforms.Resize(image_size),
        transforms.CenterCrop(image_size),
        transforms.ToTensor(),
        transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5)),
    ])

    image = Image.open(image_path).convert("RGB")
    return transform(image).unsqueeze(0)


def predict(model, image_tensor, idx_to_class, device):
    with torch.no_grad():
        outputs = model(image_tensor.to(device))
        _, predicted = torch.max(outputs, 1)
        predicted_class = idx_to_class[predicted.item()]
        confidence = torch.nn.functional.softmax(outputs, dim=1)[0][predicted.item()].item()

    return predicted_class, confidence


def main():
    # Configuration
    model_path = "image_classifier_model.pth"
    ann_file = '/Users/nepocute/PycharmProjects/ai image generate/data/annotations/stuff_train2017.json'  # Update this path
    image_path = '/Users/nepocute/PycharmProjects/ai image generate/testimages/6.jpeg'  # Update this path

    # Device configuration
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

    # Load class mapping
    idx_to_class = load_class_mapping(ann_file)
    num_classes = len(idx_to_class)

    # Load the model
    model = load_model(model_path, num_classes, device)

    # Preprocess the image
    image_tensor = preprocess_image(image_path)

    # Make prediction
    predicted_class, confidence = predict(model, image_tensor, idx_to_class, device)

    print(f"Predicted class: {predicted_class}")
    print(f"Confidence: {confidence:.2f}")


if __name__ == "__main__":
    main()