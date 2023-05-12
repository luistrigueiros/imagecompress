package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.ImageCompressorSupport;
import com.example.imagecompress.imagecompress.support.ImageFormat;

import java.io.File;

public class GifCompressor implements ImageCompressor {
    private final ImageCompressorSupport imageCompressorSupport;

    public GifCompressor(TemporaryFileStorage temporaryFileStorage) {
        this.imageCompressorSupport = new ImageCompressorSupport(temporaryFileStorage, new JpegCompressor.JpegCompressParamsFactory());
    }

    @Override
    public File compressImage(File input) throws Exception {
        return imageCompressorSupport.compressImage(input, ImageFormat.GIF);
    }
}
