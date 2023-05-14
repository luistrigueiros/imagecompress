package com.example.imagecompress.support;

import com.example.imagecompress.image.TempFileStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class TemporaryFileStorageFactory {

    public static File getBaseDirectory() throws IOException {
        return Files.createTempDirectory("TMP_IMG_STORAGE").toFile();
    }

    public static TempFileStorage createTemporaryFileStorage(File baseDirectory, Duration waitDuration)
            throws IOException {
        FileAgeWaitFilter ageWaitFilter = new FileAgeWaitFilter(waitDuration);
        return new TempFileStorage(baseDirectory, ageWaitFilter);
    }
}
