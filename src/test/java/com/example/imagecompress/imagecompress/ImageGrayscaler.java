package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.ImageFormat;

import java.io.File;
import java.io.IOException;

public interface ImageGrayscaler {
    File transformToGrayscale(File input, ImageFormat imageFormat) throws IOException;
}
