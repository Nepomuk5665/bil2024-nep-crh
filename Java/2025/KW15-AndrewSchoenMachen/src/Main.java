import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        String inputPath = "src/image.png";
        String noisedPath = "noised.png";
        String denoisedPath = "denoised.png";
        double noiseChance = 0.3;
        
        if (args.length >= 1) {
            inputPath = args[0];
        }
        if (args.length >= 2) {
            noisedPath = args[1];
        }
        if (args.length >= 3) {
            denoisedPath = args[2];
        }
        if (args.length >= 4) {
            try {
                noiseChance = Double.parseDouble(args[3]);
                if (noiseChance < 0 || noiseChance > 1) {
                    System.out.println("Noise chance must be between 0 and 1. Using default: " + noiseChance);
                    noiseChance = 0.3;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid noise chance. Using default: " + noiseChance);
            }
        }

        try {
            BufferedImage originalImage = ImageUtils.loadImage(inputPath);
            if (originalImage == null) {
                System.out.println("Error: Could not load image at " + inputPath);
                return;
            }
            
            BufferedImage noisedImage = NoiseProcessor.applyNoise(originalImage, noiseChance);
            ImageUtils.saveImage(noisedImage, noisedPath);
            System.out.println("Noised image saved to " + noisedPath);
            
            BufferedImage denoisedImage = NoiseProcessor.denoiseImageEnhanced(noisedImage, 3);
            ImageUtils.saveImage(denoisedImage, denoisedPath);
            System.out.println("Denoised image saved to " + denoisedPath);
            
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}