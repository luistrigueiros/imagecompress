package com.example.imagecompress.image;

import com.example.imagecompress.support.TemporaryFileStorageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestSupport {
    private static final Logger logger = LoggerFactory.getLogger(AllImageCompressorTest.class);

    static TemporaryFileStorage getTemporaryFileStorage() throws IOException {
        File baseDirectory = TemporaryFileStorageFactory.getBaseDirectory();
        Duration waitDuration = Duration.ofMillis(800);
        return TemporaryFileStorageFactory.createTemporaryFileStorage(baseDirectory, waitDuration);
    }
}
