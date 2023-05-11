package com.example.imagecompress.imagecompress;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCompressorSupport {
    public enum ImageFormat {
        GIF("gif"),
        JPEG("jpg"),
        PNG("png");
        public final String label;

        ImageFormat(String label) {
            this.label = label;
        }
    }
    private final TemporaryFileStorage temporaryFileStorage;

    public ImageCompressorSupport(TemporaryFileStorage temporaryFileStorage) {
        this.temporaryFileStorage = temporaryFileStorage;
    }

    void extracted(BufferedImage originalImage, ImageWriter writer, ImageWriteParam writeParam, ImageFormat imageType) throws IOException {
        File compressedFile = temporaryFileStorage.createTemporayFile("." + imageType.label);
        // Create an ImageOutputStream to write the compressed image
        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressedFile)) {

            // Set the output destination for the writer
            writer.setOutput(outputStream);

            // Write the image with the specified compression settings
            writer.write(null, new javax.imageio.IIOImage(originalImage, null, null), writeParam);
        }
    }
}
