import torch
import torch.nn as nn
import clip
from PIL import Image
import subprocess

# Check for MPS availability
if torch.backends.mps.is_available():
    device = torch.device("mps")
    print("Using MPS device")
elif torch.cuda.is_available():
    device = torch.device("cuda")
    print("Using CUDA device")
else:
    device = torch.device("cpu")
    print("Using CPU device")


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


def load_model(model_path):
    generator = ImageGenerator().to(device)
    generator.load_state_dict(torch.load(model_path, map_location=device))
    generator.eval()
    return generator


def generate_image(generator, clip_model, text_prompt, resolution):
    with torch.no_grad():
        text = clip.tokenize([text_prompt]).to(device)
        text_features = clip_model.encode_text(text).float()
        generated_image = generator(text_features)

        # Resize the image to the desired resolution
        generated_image = generated_image.squeeze(0).permute(1, 2, 0).cpu().numpy()
        generated_image = (generated_image + 1) / 2  # Denormalize
        generated_image = (generated_image * 255).astype('uint8')
        pil_image = Image.fromarray(generated_image)
        pil_image = pil_image.resize(resolution, Image.LANCZOS)

        return pil_image


def get_user_input():
    print("\nWelcome to the Image Generator!")

    while True:
        text_prompt = input("Enter your text prompt (or 'quit' to exit): ")
        if text_prompt.lower() == 'quit':
            return None, None

        print("\nSelect the desired resolution:")
        print("1. HD (1280x720)")
        print("2. 4K (3840x2160)")
        print("3. 8K (7680x4320)")

        while True:
            choice = input("Enter your choice (1-3): ")
            if choice in ['1', '2', '3']:
                resolutions = {
                    '1': ("hd", (1280, 720)),
                    '2': ("4k", (3840, 2160)),
                    '3': ("8k", (7680, 4320))
                }
                return text_prompt, resolutions[choice]
            else:
                print("Invalid choice. Please try again.")


def main():
    model_path = "text_to_image_model.pth"
    generator = load_model(model_path)
    clip_model, _ = clip.load("ViT-B/32", device=device)

    while True:
        user_input = get_user_input()
        if user_input is None:
            print("Thank you for using the Image Generator. Goodbye!")
            break

        text_prompt, (res_name, resolution) = user_input
        print(f"\nGenerating {res_name.upper()} image for prompt: '{text_prompt}'")
        generated_image = generate_image(generator, clip_model, text_prompt, resolution)

        output_filename = f"generated_{res_name}_{text_prompt.replace(' ', '_')}.png"
        generated_image.save(output_filename)
        print(f"Image saved as {output_filename}")

        # Open the image using the 'open' command on Mac
        subprocess.run(["open", output_filename])

        print("\n")  # Add a newline for better readability


if __name__ == "__main__":
    main()