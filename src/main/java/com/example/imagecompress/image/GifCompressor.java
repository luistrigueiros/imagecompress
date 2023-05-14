package com.example.imagecompress.image;

import com.example.imagecompress.support.ImageCompressorSupport;
import com.example.imagecompress.support.ImageFormat;
import org.springframework.util.StringUtils;

import java.io.File;

public class GifCompressor implements ImageCompressor {
    private final ImageCompressorSupport imageCompressorSupport;

    public GifCompressor(TempFileStorage tempFileStorage) {
        this.imageCompressorSupport = new ImageCompressorSupport(tempFileStorage, new JpegCompressParamsFactory());
    }

    @Override
    public File compressImage(File input) throws Exception {
        return imageCompressorSupport.compressImage(input, ImageFormat.GIF);
    }

    @Override
    public Boolean canHandle(String type) {
        return StringUtils.endsWithIgnoreCase(ImageFormat.GIF.label, type);
    }

    @Override
    public ImageFormat getSupportImageFormat() {
        return ImageFormat.GIF;
    }
}
