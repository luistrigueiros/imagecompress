package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.ImageFormat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToGrayscale2 {
    private final TemporaryFileStorage temporaryFileStorage;

    public ImageToGrayscale2(TemporaryFileStorage temporaryFileStorage) {
        this.temporaryFileStorage = temporaryFileStorage;
    }

    public File transformToGrayscale(File input, ImageFormat imageFormat) throws IOException {
        BufferedImage originalImage = ImageIO.read(input);
        BufferedImage grayscaleImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics graphics = grayscaleImage.getGraphics();
        graphics.drawImage(originalImage, 0, 0, null);
        graphics.dispose();
        File output = temporaryFileStorage.createTemporayFile("." + imageFormat.label);
        ImageIO.write(grayscaleImage,imageFormat.label, output);
        return output;
    }
}
