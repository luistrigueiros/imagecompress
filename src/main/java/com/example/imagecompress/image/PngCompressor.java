package com.example.imagecompress.image;

import com.example.imagecompress.support.ImageCompressorSupport;
import com.example.imagecompress.support.ImageFormat;
import org.springframework.util.StringUtils;

import java.io.File;


public class PngCompressor implements ImageCompressor {

    private final ImageCompressorSupport imageCompressorSupport;

    public PngCompressor(TempFileStorage tempFileStorage) {
//        this.imageCompressorSupport = new ImageCompressorSupport(temporaryFileStorage, new PngCompressParamsFactory());
        this.imageCompressorSupport = new ImageCompressorSupport(tempFileStorage, new JpegCompressParamsFactory());
    }

    @Override
    public File compressImage(File input) throws Exception {
        return imageCompressorSupport.compressImage(input, ImageFormat.PNG);
    }

    @Override
    public Boolean canHandle(String type) {
        return StringUtils.endsWithIgnoreCase(ImageFormat.PNG.label, type);
    }

    @Override
    public ImageFormat getSupportImageFormat() {
        return ImageFormat.PNG;
    }
}
