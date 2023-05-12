package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.ImageFormat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageToGrayscale3 implements ImageGrayscaler {
    private final TemporaryFileStorage temporaryFileStorage;

    public ImageToGrayscale3(TemporaryFileStorage temporaryFileStorage) {
        this.temporaryFileStorage = temporaryFileStorage;
    }

    @Override
    public File transformToGrayscale(File input, ImageFormat imageFormat) throws IOException {
        BufferedImage originalImage = ImageIO.read(input);
//        BufferedImageOp filter = new GrayFilter(true, 50);
//        BufferedImageFilter producer = new BufferedImageFilter(filter);

        ImageFilter filter = new GrayFilter(true, 50);
        ImageProducer producer = new FilteredImageSource(originalImage.getSource(), filter);
        Image image = Toolkit.getDefaultToolkit().createImage(producer);

        BufferedImage grayscaleImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics graphics = grayscaleImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();


        File output = temporaryFileStorage.createTemporayFile("." + imageFormat.label);
        ImageIO.write(grayscaleImage, imageFormat.label, output);
        return output;
    }
}
