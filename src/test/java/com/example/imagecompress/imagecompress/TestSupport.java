package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.TemporaryFileStorageFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestSupport {
    private static final Logger logger = LoggerFactory.getLogger(JpegCompressorTest.class);
    static float getCompressedPercentage(File file, File compressed) {
        long inputSize = FileUtils.sizeOf(file);
        long outputSize = FileUtils.sizeOf(compressed);
        Assertions.assertTrue(inputSize > outputSize);
        float compressedPercentage = 100 - ((float) outputSize / inputSize) * 100;
        logger.debug("inputSize={},  outputSize={}, compression={}", inputSize, outputSize,compressedPercentage);
        return compressedPercentage;
    }

    static TemporaryFileStorage getTemporaryFileStorage() throws IOException {
        File baseDirectory = TemporaryFileStorageFactory.getBaseDirectory();
        Duration waitDuration = Duration.ofMillis(800);
        return TemporaryFileStorageFactory.createTemporaryFileStorage(baseDirectory, waitDuration);
    }
}