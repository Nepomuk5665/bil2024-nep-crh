import os
import subprocess
import sys
import torch
from diffusers import DiffusionPipeline
from moviepy.editor import ImageSequenceClip
import tempfile

def find_ffmpeg():
    try:
        result = subprocess.run(["which", "ffmpeg"], capture_output=True, text=True, check=True)
        return result.stdout.strip()
    except subprocess.CalledProcessError:
        return None

# Set ffmpeg path
ffmpeg_path = find_ffmpeg()
if ffmpeg_path:
    os.environ["IMAGEIO_FFMPEG_EXE"] = ffmpeg_path
else:
    print("ffmpeg not found. Please install it using 'brew install ffmpeg' and try again.")
    sys.exit(1)

def generate_video(prompt, num_frames=120, fps=30):
    # Load the model
    pipe = DiffusionPipeline.from_pretrained("stabilityai/stable-diffusion-xl-base-1.0", torch_dtype=torch.float16, use_safetensors=True, variant="fp16")
    pipe = pipe.to("mps")  # Use MPS (Metal Performance Shaders) for Mac

    # Generate frames
    frames = []
    for i in range(num_frames):
        print(f"Generating frame {i+1}/{num_frames}")
        image = pipe(prompt, num_inference_steps=50).images[0]
        frames.append(image)

    # Create a temporary directory to store frames
    with tempfile.TemporaryDirectory() as tmpdirname:
        # Save frames as images
        frame_files = []
        for i, frame in enumerate(frames):
            filename = os.path.join(tmpdirname, f"frame_{i:03d}.png")
            frame.save(filename)
            frame_files.append(filename)

        # Create video from frames
        clip = ImageSequenceClip(frame_files, fps=fps)
        output_filename = "output_video.mp4"
        clip.write_videofile(output_filename, codec="libx264")

    print(f"Video saved as {output_filename}")
    return output_filename

def open_file(filename):
    try:
        subprocess.run(["open", filename], check=True)
        print(f"Opened {filename}")
    except subprocess.CalledProcessError:
        print(f"Failed to open {filename}. Please open it manually.")

if __name__ == "__main__":
    prompt = input("Enter your prompt: ")
    try:
        output_file = generate_video(prompt)
        open_file(output_file)
    except Exception as e:
        print(f"An error occurred: {str(e)}")
        if "MPS" in str(e):
            print("If you're seeing an MPS-related error, try changing 'mps' to 'cpu' in the script.")
        elif "CUDA" in str(e):
            print("If you're seeing a CUDA-related error, make sure you have a compatible GPU and CUDA installed.")