package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.ImageCompressorSupport;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class GifCompressor implements ImageCompressor {
    private final ImageCompressorSupport imageCompressorSupport;

    public GifCompressor(TemporaryFileStorage temporaryFileStorage) {
        this.imageCompressorSupport = new ImageCompressorSupport(temporaryFileStorage, new JpegCompressor.JpegCompressParamsFactory());
    }

    @Override
    public File compress(File input) throws Exception {
        BufferedImage gifImage = ImageIO.read(input);
        return imageCompressorSupport.compressImage(gifImage, ImageCompressorSupport.ImageFormat.GIF);
    }
}
