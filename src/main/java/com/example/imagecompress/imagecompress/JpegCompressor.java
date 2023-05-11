package com.example.imagecompress.imagecompress;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;

public class JpegCompressor {
    private final ImageCompressorSupport imageCompressorSupport;

    static class JpegCompressParamsFactory implements CompressParamsFactory {

        @Override
        public ImageWriter getWriter() {
            return ImageIO.getImageWritersByFormatName("jpeg").next();
        }

        @Override
        public ImageWriteParam getImageWriteParam(ImageWriter writer) {
            return buildImageWriteParam(writer);
        }

        private ImageWriteParam buildImageWriteParam(ImageWriter writer) {
            // Set the compression quality to 0.5
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.5f);
            return param;
        }

    };

    public JpegCompressor(TemporaryFileStorage temporaryFileStorage) {
        this.imageCompressorSupport = new ImageCompressorSupport(temporaryFileStorage, new JpegCompressParamsFactory());
    }

    public void main() throws Exception {
        BufferedImage image = ImageIO.read(new File("input.jpg"));
        File file = imageCompressorSupport.compressImage(image, ImageCompressorSupport.ImageFormat.JPEG);
    }

}
