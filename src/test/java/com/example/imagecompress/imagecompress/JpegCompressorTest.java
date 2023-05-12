package com.example.imagecompress.imagecompress;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;

import static com.example.imagecompress.imagecompress.TestSupport.getTemporaryFileStorage;

class JpegCompressorTest {
    private static final Logger logger = LoggerFactory.getLogger(JpegCompressorTest.class);
    @Test
    void simpleTest() throws Exception {
        ImageCompressor imageCompressor = new JpegCompressor(getTemporaryFileStorage());
        File file = Paths.get("sample/input.jpg").toFile();
        File compressed = imageCompressor.compressImage(file);
        float compressedPercentage = TestSupport.getCompressedPercentage(file, compressed);
        Assertions.assertTrue( compressedPercentage < 100);
    }
}
