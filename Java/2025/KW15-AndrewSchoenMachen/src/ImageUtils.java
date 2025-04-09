import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    public static BufferedImage loadImage(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("File does not exist: " + path);
                return null;
            }
            return ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
    
    public static void saveImage(BufferedImage image, String path) {
        try {
            File outputFile = new File(path);
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }
    
    public static BufferedImage deepCopy(BufferedImage source) {
        BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                copy.setRGB(x, y, source.getRGB(x, y));
            }
        }
        return copy;
    }
}