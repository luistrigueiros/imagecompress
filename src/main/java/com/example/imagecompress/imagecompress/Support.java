package com.example.imagecompress.imagecompress;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class Support {
    static void extracted(BufferedImage originalImage, ImageWriter writer, ImageWriteParam writeParam, File compressedFile) throws IOException {
        // Create an ImageOutputStream to write the compressed image
        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressedFile)) {

            // Set the output destination for the writer
            writer.setOutput(outputStream);

            // Write the image with the specified compression settings
            writer.write(null, new javax.imageio.IIOImage(originalImage, null, null), writeParam);
        }
    }
}
