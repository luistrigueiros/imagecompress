package com.example.imagecompress;

import com.example.imagecompress.image.ImageCompressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.Set;

@Service
public class ImageCompressService {
    private static final Logger logger = LoggerFactory.getLogger(ImageCompressService.class);
    private final Set<ImageCompressor> imageCompressors;
    public ImageCompressService(Set<ImageCompressor> imageCompressors) {
        this.imageCompressors = imageCompressors;
        logger.info("Created instance with {} compressors", imageCompressors.size());
    }

    public Optional<File> compress(File file, String type) {
        logger.debug("Compressing file={}, type={}", file.getAbsolutePath(), type);
        return imageCompressors
                .stream()
                .filter(it -> it.canHandle(type))
                .findFirst()
                .map(it -> compressImage(it, file));
    }

    private File compressImage(ImageCompressor imageCompressor, File file) {
        try {
            return imageCompressor.compressImage(file);
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
