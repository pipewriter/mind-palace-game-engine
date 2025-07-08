import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RawToPng {
    public static byte[] rawToPng(int[] rawImageData, int width, int height) throws IOException {
        // Create a BufferedImage from the raw image data
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = rawImageData[index];
                bufferedImage.setRGB(x, y, pixel);
                index++;
            }
        }

        // Write the BufferedImage to a byte array in PNG format
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        }
    }

    // public static void main(String[] args) {
    //     // Example usage
    //     // Creating a raw image data array with a simple gradient
    //     int width = 100;
    //     int height = 100;
    //     byte[] rawImageData = new byte[width * height * 4]; // ARGB for each pixel
    //     int index = 0;
    //     for (int y = 0; y < height; y++) {
    //         for (int x = 0; x < width; x++) {
    //             rawImageData[index] = (byte) 255; // Alpha
    //             rawImageData[index + 1] = (byte) ((x * 255) / (width - 1)); // Red
    //             rawImageData[index + 2] = (byte) ((y * 255) / (height - 1)); // Green
    //             rawImageData[index + 3] = (byte) (((x + y) * 255) / (width + height - 2)); // Blue
    //             index += 4;
    //         }
    //     }

    //     try {
    //         byte[] pngData = rawToPng(rawImageData, width, height);
    //         System.out.println("PNG data length: " + pngData.length);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}