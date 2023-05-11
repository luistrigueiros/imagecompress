package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.ImageCompressorSupport;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class GifCompressor {
    private final ImageCompressorSupport imageCompressorSupport;
    public GifCompressor(TemporaryFileStorage temporaryFileStorage) {
        this.imageCompressorSupport = new ImageCompressorSupport(temporaryFileStorage, new JpegCompressor.JpegCompressParamsFactory());
    }

    public void main() throws Exception {
        // Load the GIF image
        BufferedImage gifImage = ImageIO.read(new File("input.gif"));
        imageCompressorSupport.compressImage(gifImage, ImageCompressorSupport.ImageFormat.GIF);

    }
}
