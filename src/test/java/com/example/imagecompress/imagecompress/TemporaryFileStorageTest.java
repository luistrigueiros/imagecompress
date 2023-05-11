package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.FileAgeWaitFilter;
import org.apache.commons.io.FileUtils;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


class TemporaryFileStorageTest {
    private File baseDirectory;

    @BeforeEach
    void init() throws IOException {
        baseDirectory = getBaseDirectory();
        FileUtils.cleanDirectory(baseDirectory);
        Awaitility.setDefaultPollDelay(500, TimeUnit.MILLISECONDS);
        Awaitility.setDefaultTimeout(Duration.ofMinutes(1));
    }

    @Test
    void simpleHappyPathTest() throws IOException {
        Duration waitDuration = Duration.ofMillis(800);
        FileAgeWaitFilter ageWaitFilter = new FileAgeWaitFilter(waitDuration);
        TemporaryFileStorage storage = new TemporaryFileStorage(baseDirectory, ageWaitFilter);
        File file = storage.createTemporayFile(".png");
        FileUtils.copyFile(Paths.get("sample/input.jpg").toFile(), file);
        Instant afterWaitDuration = Instant.now().plus(Duration.ofSeconds(1));
        Awaitility.await().until(() -> afterWaitDuration.compareTo(Instant.now()) < 0);
        long files = storage.cleanUpTempFiles(10);
        assertEquals(1, files);
        assertTrue( FileUtils.isEmptyDirectory(baseDirectory) );
        assertFalse(file.exists());
    }

    private File getBaseDirectory() throws IOException {
        return Files.createTempDirectory("TMP_IMG_STORAGE").toFile();
    }
}
