package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.CompressParamsFactory;
import com.example.imagecompress.imagecompress.support.ImageCompressorSupport;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PngCompressor {
    private static class xxx implements CompressParamsFactory {

        @Override
        public ImageWriter getWriter() {
            return ImageIO.getImageWritersByFormatName("png").next();
        }

        @Override
        public ImageWriteParam getImageWriteParam(ImageWriter writer) {
            return buildImageWriteParam(writer);
        }
        private ImageWriteParam buildImageWriteParam(ImageWriter writer) {
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

    private final ImageCompressorSupport imageCompressorSupport;

    public PngCompressor(TemporaryFileStorage temporaryFileStorage) {
        this.imageCompressorSupport = new ImageCompressorSupport(temporaryFileStorage, new xxx());
    }

    public void main() {
        try {
            // Load the original PNG image
            BufferedImage originalImage = ImageIO.read(new File("input.png"));

            imageCompressorSupport.compressImage(originalImage, ImageCompressorSupport.ImageFormat.PNG);
//            writer.dispose();

            System.out.println("Image compression completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
