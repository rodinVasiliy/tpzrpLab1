package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {

    public static BufferedImage getImageFromFile(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static byte[] getImageBytes(BufferedImage image) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpeg", os);
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void saveImageToFile(BufferedImage resourceImage, String imagePath) {
        try{
            File f = new File(imagePath);
            String fileExtension = imagePath.split("\\.")[1];
            ImageIO.write(resourceImage, fileExtension, f);
        } catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
