import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import javax.imageio.ImageIO;

public class Base64ToImage {

    public static void main(String[] args) throws IOException {
        // Read the Base64 string from the file
        String base64String = new String(Files.readAllBytes(Paths.get("/Users/nepocute/Documents/C-Projects/bil2024-nep-crh/Java/2024/KW47-03-Base64toImage/src/base64.txt")));


        if (base64String.startsWith("data:image")) {
            base64String = base64String.substring(base64String.indexOf(",") + 1);
        }

        base64String = base64String.replace("\\", "");


        byte[] imageBytes = Base64.getDecoder().decode(base64String);


        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(bis);
        bis.close();


        File outputfile = new File("output.png");
        ImageIO.write(image, "png", outputfile);

        System.out.println("Image generated successfully!");
    }
}