import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoiseProcessor {
    private static final int BLACK_RGB = Color.BLACK.getRGB();
    private static final int WHITE_RGB = Color.WHITE.getRGB();
    
    public static BufferedImage applyNoise(BufferedImage image, double noiseChance) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage noisedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Random random = new Random();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int originalRGB = image.getRGB(x, y);
                
                if (random.nextDouble() < noiseChance) {
                    int corruptedRGB = random.nextBoolean() ? BLACK_RGB : WHITE_RGB;
                    noisedImage.setRGB(x, y, corruptedRGB);
                } else {
                    noisedImage.setRGB(x, y, originalRGB);
                }
            }
        }
        
        return noisedImage;
    }
    
    public static BufferedImage denoiseImageEnhanced(BufferedImage noisedImage, int passes) {
        BufferedImage currentImage = ImageUtils.deepCopy(noisedImage);
        
        for (int pass = 0; pass < passes; pass++) {
            currentImage = singleDenoisePass(currentImage, pass+1);
        }
        
        return currentImage;
    }
    
    private static BufferedImage singleDenoisePass(BufferedImage image, int radius) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage denoisedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int currentRGB = image.getRGB(x, y);
                
                if (currentRGB == BLACK_RGB || currentRGB == WHITE_RGB) {
                    List<Color> surroundingColors = getExpandedSurroundingColors(image, x, y, radius);
                    
                    if (!surroundingColors.isEmpty()) {
                        Color averageColor = calculateAverageColor(surroundingColors);
                        denoisedImage.setRGB(x, y, averageColor.getRGB());
                    } else {
                        denoisedImage.setRGB(x, y, Color.GRAY.getRGB());
                    }
                } else {
                    denoisedImage.setRGB(x, y, currentRGB);
                }
            }
        }
        
        return denoisedImage;
    }
    
    private static List<Color> getExpandedSurroundingColors(BufferedImage image, int x, int y, int radius) {
        List<Color> colors = new ArrayList<>();
        int width = image.getWidth();
        int height = image.getHeight();
        
        for (int j = Math.max(0, y - radius); j <= Math.min(height - 1, y + radius); j++) {
            for (int i = Math.max(0, x - radius); i <= Math.min(width - 1, x + radius); i++) {
                if (i == x && j == y) {
                    continue;
                }
                
                int rgb = image.getRGB(i, j);
                
                if (rgb != BLACK_RGB && rgb != WHITE_RGB) {
                    int distance = Math.abs(i - x) + Math.abs(j - y);
                    Color pixelColor = new Color(rgb);
                    
                    int weight = radius + 2 - distance;
                    for (int w = 0; w < weight; w++) {
                        colors.add(pixelColor);
                    }
                }
            }
        }
        
        return colors;
    }
    
    private static Color calculateAverageColor(List<Color> colors) {
        if (colors.isEmpty()) {
            return Color.GRAY;
        }
        
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        
        for (Color color : colors) {
            totalRed += color.getRed();
            totalGreen += color.getGreen();
            totalBlue += color.getBlue();
        }
        
        int avgRed = totalRed / colors.size();
        int avgGreen = totalGreen / colors.size();
        int avgBlue = totalBlue / colors.size();
        
        return new Color(avgRed, avgGreen, avgBlue);
    }
}