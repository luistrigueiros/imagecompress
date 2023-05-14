package com.example.imagecompress.image;

import com.example.imagecompress.support.ImageCompressorSupport;
import com.example.imagecompress.support.ImageFormat;
import org.springframework.util.StringUtils;

import java.io.File;

public class JpegCompressor implements ImageCompressor {

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

    public JpegCompressor(TempFileStorage tempFileStorage) {
        this.imageCompressorSupport = new ImageCompressorSupport(tempFileStorage, new JpegCompressParamsFactory());
    }
}
