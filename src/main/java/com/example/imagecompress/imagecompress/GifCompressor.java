package com.example.imagecompress.imagecompress;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;

public class GifCompressor {
    private final Support support;

    public GifCompressor(Support support) {
        this.support = support;
    }

    public void main() throws Exception {
        // Load the GIF image
        BufferedImage gifImage = ImageIO.read(new File("input.gif"));
        
        // Create an ImageWriter for GIF format
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageWriteParam writeParam = buildImageWriteParam(writer);

        // Create an output file
        File output = new File("build/output.gif");
        support.extracted(gifImage, writer, writeParam, output);

        // Close the writer and output stream
        writer.dispose();
    }

    private ImageWriteParam buildImageWriteParam(ImageWriter writer) {
        // Set the compression parameters
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionQuality(0.5f);
        return writeParam;
    }
}
