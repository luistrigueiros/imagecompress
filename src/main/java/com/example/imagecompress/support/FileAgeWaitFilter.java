package com.example.imagecompress.support;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.function.Predicate;

public class FileAgeWaitFilter implements Predicate<File> {
    private static final Logger logger = LoggerFactory.getLogger(FileAgeWaitFilter.class);
    private final Duration pickUpWaitDuration;

    public FileAgeWaitFilter(Duration pickUpWaitDuration) {
        this.pickUpWaitDuration = pickUpWaitDuration;
    }

    @Override
    public boolean test(File file) {
        boolean fileOlder = FileUtils.isFileOlder(file, Date.from(Instant.now().minus(pickUpWaitDuration)));
        String absolutePath = file.getAbsolutePath();
        if (!fileOlder) {
            logger.trace("Not allowing file={}", absolutePath);
        } else {
            logger.trace("Allowing file={}", absolutePath);
        }
        return fileOlder;
    }
}
