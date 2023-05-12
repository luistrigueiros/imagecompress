package com.example.imagecompress.image;

import java.io.File;

public interface ImageCompressor {
    File compressImage(File input) throws Exception;
}
