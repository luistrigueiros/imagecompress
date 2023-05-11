package com.example.imagecompress.imagecompress;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PngCompressionExample {
    private final ImageCompressorSupport imageCompressorSupport;

    public PngCompressionExample(TemporaryFileStorage temporaryFileStorage) {
        this.imageCompressorSupport = new ImageCompressorSupport(temporaryFileStorage);
    }

    public void main() {
        try {
            // Load the original PNG image
            BufferedImage originalImage = ImageIO.read(new File("input.png"));

            // Get the writer for PNG format
            ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();

            ImageWriteParam writeParam = buildImageWriteParam(writer);

            // Create a file to save the compressed image
            File compressedFile = new File("build/output.png");

            imageCompressorSupport.extracted(originalImage, writer, writeParam, ImageCompressorSupport.ImageFormat.PNG);
            writer.dispose();

            System.out.println("Image compression completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ImageWriteParam buildImageWriteParam(ImageWriter writer) {
        // Create an ImageWriteParam to specify compression settings
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        // Set the compression mode to MODE_EXPLICIT and the compression type
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionType("Deflate");
        // Set the compression quality (0.0 to 1.0, where 1.0 is highest quality)
        writeParam.setCompressionQuality(0.5f);
        return writeParam;
    }
}
