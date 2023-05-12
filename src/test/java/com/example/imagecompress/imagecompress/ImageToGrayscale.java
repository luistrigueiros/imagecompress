package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.ImageFormat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToGrayscale implements ImageGrayscaler {
    private final TemporaryFileStorage temporaryFileStorage;

    public ImageToGrayscale(TemporaryFileStorage temporaryFileStorage) {
        this.temporaryFileStorage = temporaryFileStorage;
    }

    @Override
    public File transformToGrayscale(File input, ImageFormat imageFormat) throws IOException {
        BufferedImage originalImage = ImageIO.read(input);
        // Create a new image with the same dimensions as the original
        BufferedImage grayscaleImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        // Iterate through each pixel of the original image and calculate its grayscale value
        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                // Get the color of the pixel at (x, y)
                Color originalColor = new Color(originalImage.getRGB(x, y));

                // Calculate the grayscale value of the pixel
                int grayValue = (int) (0.21 * originalColor.getRed() + 0.72 * originalColor.getGreen() + 0.07 * originalColor.getBlue());

                // Set the pixel in the grayscale image to the calculated grayscale value
                Color grayColor = new Color(grayValue, grayValue, grayValue);
                grayscaleImage.setRGB(x, y, grayColor.getRGB());
            }
        }
        File output = temporaryFileStorage.createTemporayFile("." + imageFormat.label);
        ImageIO.write(grayscaleImage,imageFormat.label, output);
        return output;
    }
}
