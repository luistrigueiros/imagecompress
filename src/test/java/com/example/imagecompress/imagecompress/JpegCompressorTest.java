package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.TemporaryFileStorageFactory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

class JpegCompressorTest {
    @Test
    void simpleTest() throws Exception {
        File baseDirectory = TemporaryFileStorageFactory.getBaseDirectory();
        Duration waitDuration = Duration.ofMillis(800);
        TemporaryFileStorage storage = TemporaryFileStorageFactory.createTemporaryFileStorage(baseDirectory, waitDuration);
        JpegCompressor jpegCompressor = new JpegCompressor(storage);
        File file = Paths.get("sample/input.gif").toFile();
        jpegCompressor.compress(file);
    }
}
