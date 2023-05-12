package com.example.imagecompress.image;

import com.example.imagecompress.support.ImageFormat;

import java.io.File;

public interface ImageCompressor {
    File compressImage(File input) throws Exception;
    Boolean canHandle(String type);

    ImageFormat getSupportImageFormat();
}
