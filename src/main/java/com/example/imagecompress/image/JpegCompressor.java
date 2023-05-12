package com.example.imagecompress.image;

import com.example.imagecompress.support.CompressParamsFactory;
import com.example.imagecompress.support.ImageCompressorSupport;
import com.example.imagecompress.support.ImageFormat;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.io.File;

public class JpegCompressor implements ImageCompressor {
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
            ImageWriteParam param = new JPEGImageWriteParam(null);
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.3f);
            return param;
        }
    }

    private final ImageCompressorSupport imageCompressorSupport;

    @Override
    public File compressImage(File input) throws Exception {
        return imageCompressorSupport.compressImage(input, ImageFormat.JPEG);
    }

    @Override
    public Boolean canHandle(String type) {
        return StringUtils.endsWithIgnoreCase(ImageFormat.JPEG.label, type);
    }

    @Override
    public ImageFormat getSupportImageFormat() {
        return ImageFormat.JPEG;
    }

    public JpegCompressor(TemporaryFileStorage temporaryFileStorage) {
        this.imageCompressorSupport = new ImageCompressorSupport(temporaryFileStorage, new JpegCompressParamsFactory());
    }
}
