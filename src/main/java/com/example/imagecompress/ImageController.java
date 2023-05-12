package com.example.imagecompress;

import com.example.imagecompress.image.ImageCompressor;
import com.example.imagecompress.image.TemporaryFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Controller
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final Set<ImageCompressor> imageCompressors;

    private final TemporaryFileStorage temporaryFileStorage;
    public ImageController(TemporaryFileStorage temporaryFileStorage,Set<ImageCompressor> imageCompressors) {
        this.imageCompressors = imageCompressors;
        this.temporaryFileStorage = temporaryFileStorage;
        logger.info("Created instance with {} compressors", imageCompressors.size());
    }

    @PostMapping("/compress")
    public ResponseEntity<InputStreamResource> compress(
            @RequestParam MultipartFile file,
            @RequestParam String type) {
        return imageCompressors
                .stream()
                .filter(it -> it.canHandle(type))
                .findFirst()
                .map(it -> compressImage(it, file))
                .map(this::toResponse)
                .orElseGet(this::toFailResponse);
    }

    private File compressImage(ImageCompressor imageCompressor, MultipartFile file) {
        try {
            File temporayFile = temporaryFileStorage.createTemporayFile("." + imageCompressor.getSupportImageFormat().label);
            file.transferTo(temporayFile);
            return imageCompressor.compressImage(temporayFile);
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private ResponseEntity<InputStreamResource> toResponse(File file) {
        try {
            Path path = file.toPath();
            InputStream inputStream = Files.newInputStream(path);
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(Files.size(path));
            headers.setContentType(MediaType.valueOf(Files.probeContentType(path)));
            return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private ResponseEntity<InputStreamResource> toFailResponse() {
        HttpHeaders headers = new HttpHeaders();
        byte[] bytes = "Error processing".getBytes(StandardCharsets.UTF_8);
        InputStreamResource inputStreamResource = new InputStreamResource( new ByteArrayInputStream(bytes));
        return new ResponseEntity<>(inputStreamResource, headers , HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
