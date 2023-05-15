package com.example.imagecompress.image;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TempFileStorage {
    private final static Logger logger = LoggerFactory.getLogger(TempFileStorage.class);
    private final File baseDirectory;
    private final Predicate<File> fileDeleteFilter;

    public TempFileStorage(File baseDirectory, Predicate<File> fileDeleteFilter) throws IOException {
        this.baseDirectory = baseDirectory;
        this.fileDeleteFilter = fileDeleteFilter;
        if (!baseDirectory.exists()) {
            logger.info("Creating base directory {}", baseDirectory.getAbsolutePath());
            FileUtils.forceMkdir(baseDirectory);
        }
        logger.info("Using baseDirectory={}", baseDirectory.getAbsolutePath());
        FileUtils.forceDeleteOnExit(baseDirectory);
    }

    public File createTempFile(String suffix) throws IOException {
        File file = File.createTempFile("temp-img", suffix, baseDirectory);
        logger.debug("Created temp file {}", file.getAbsolutePath());
        return file;
    }

    public long cleanUpTempFiles(int maxFilesToPurge) throws IOException {
        logger.trace("Searching for temporary files to delete in {}", baseDirectory.getAbsolutePath());
        try (Stream<Path> stream = Files.list(baseDirectory.toPath())) {
            long total = stream.map(Path::toFile)
                    .filter(fileDeleteFilter)
                    .limit(maxFilesToPurge)
                    .peek(this::deleteFile)
                    .count();
            logger.trace("Deleted {} files", total);
            return total;
        }
    }

    public File getBaseDirectory() {
        return baseDirectory;
    }

    private boolean deleteFile(File file) {
        logger.trace("About to delete file {}", file.getAbsolutePath());
        boolean deleted = FileUtils.deleteQuietly(file);
        logger.trace("File {} deleted={}", file.getAbsolutePath(), deleted);
        return deleted;
    }
}
