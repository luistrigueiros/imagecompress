package com.example.imagecompress.image;

import com.example.imagecompress.support.TemporaryFileStorageFactory;
import org.apache.commons.io.FileUtils;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


class TempFileStorageTest {
    private File baseDirectory;

    @BeforeEach
    void init() throws IOException {
        baseDirectory = TemporaryFileStorageFactory.getBaseDirectory();
        FileUtils.cleanDirectory(baseDirectory);
        Awaitility.setDefaultPollDelay(500, TimeUnit.MILLISECONDS);
        Awaitility.setDefaultTimeout(Duration.ofMinutes(1));
    }

    @Test
    void simpleHappyPathTest() throws IOException {
        Duration waitDuration = Duration.ofMillis(800);
        TempFileStorage storage = TemporaryFileStorageFactory.createTemporaryFileStorage(baseDirectory, waitDuration);
        File file = storage.createTempFile(".png");
        FileUtils.copyFile(Paths.get("sample/input.jpg").toFile(), file);
        Instant afterWaitDuration = Instant.now().plus(Duration.ofSeconds(1));
        Awaitility.await().until(() -> afterWaitDuration.compareTo(Instant.now()) < 0);
        long files = storage.cleanUpTempFiles(10);
        assertEquals(1, files);
        assertTrue(FileUtils.isEmptyDirectory(baseDirectory));
        assertFalse(file.exists());
    }
}
