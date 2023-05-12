package com.example.imagecompress;

import com.example.imagecompress.image.TemporaryFileStorage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TemporaryStorageCleaner {
    private final TemporaryFileStorage temporaryFileStorage;

    public TemporaryStorageCleaner(TemporaryFileStorage temporaryFileStorage) {
        this.temporaryFileStorage = temporaryFileStorage;
    }

    @Scheduled(fixedDelay = 20_000)
    public void scheduleFixedDelayTask() throws IOException {
        temporaryFileStorage.cleanUpTempFiles(10);
    }
}
