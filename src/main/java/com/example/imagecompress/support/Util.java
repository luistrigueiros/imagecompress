package com.example.imagecompress.support;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static float getCompressedPercentage(File file, File compressed, boolean doAssert) {
        long inputSize = FileUtils.sizeOf(file);
        long outputSize = FileUtils.sizeOf(compressed);
        float compressedPercentage = 100 - ((float) outputSize / inputSize) * 100;
        logger.debug("inputSize={},  outputSize={}, compression={}", inputSize, outputSize,compressedPercentage);
        if (doAssert) {
            Assert.isTrue( inputSize > outputSize, "Input is bigger than output");
        }
        return compressedPercentage;
    }

    public static float getCompressedPercentage(File file, File compressed) {
        return getCompressedPercentage(file, compressed, false);
    }
}
