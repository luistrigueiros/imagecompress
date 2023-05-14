package com.example.imagecompress;

import com.example.imagecompress.image.TempFileStorage;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class TemporaryStorageCleaner implements DisposableBean {
    private Logger logger = LoggerFactory.getLogger(TemporaryStorageCleaner.class);
    private final TempFileStorage tempFileStorage;

    public TemporaryStorageCleaner(TempFileStorage tempFileStorage) {
        this.tempFileStorage = tempFileStorage;
    }

    @Scheduled(fixedDelay = 20_000)
    public void scheduleFixedDelayTask() throws IOException {
        tempFileStorage.cleanUpTempFiles(10);
    }

    @Override
    public void destroy() throws Exception {
        File baseDirectory = tempFileStorage.getBaseDirectory();
        logger.info("Clean up baseDirectory=[{}]", baseDirectory);
        FileUtils.forceDelete(baseDirectory);
    }
}
