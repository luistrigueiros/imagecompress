package com.example.imagecompress.support;

import com.example.imagecompress.image.TemporaryFileStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class TemporaryFileStorageFactory {

    public static File getBaseDirectory() throws IOException {
        return Files.createTempDirectory("TMP_IMG_STORAGE").toFile();
    }

    public static TemporaryFileStorage createTemporaryFileStorage(File baseDirectory, Duration waitDuration) throws IOException {
        FileAgeWaitFilter ageWaitFilter = new FileAgeWaitFilter(waitDuration);
        return new TemporaryFileStorage(baseDirectory, ageWaitFilter);
    }
}
