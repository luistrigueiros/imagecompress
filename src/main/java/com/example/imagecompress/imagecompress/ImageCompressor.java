package com.example.imagecompress.imagecompress;

import java.io.File;

public interface ImageCompressor {
    File compress(File input) throws Exception;
}
