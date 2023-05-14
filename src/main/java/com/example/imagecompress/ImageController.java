package com.example.imagecompress;

import com.example.imagecompress.image.ImageCompressor;
import com.example.imagecompress.image.TemporaryFileStorage;
import com.example.imagecompress.support.ImageCompressService;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Controller
public class ImageController {
    private final ImageCompressService imageCompressService;

    private final TemporaryFileStorage temporaryFileStorage;

    public ImageController(TemporaryFileStorage temporaryFileStorage, ImageCompressService imageCompressService) {
        this.imageCompressService = imageCompressService;
        this.temporaryFileStorage = temporaryFileStorage;
    }

    @PostMapping("/compress")
    public ResponseEntity<InputStreamResource> compress(
            @RequestParam MultipartFile file,
            @RequestParam String type) throws IOException {
        File temporayFile = temporaryFileStorage.createTemporayFile("." + type);
        file.transferTo(temporayFile);
        return imageCompressService.compress(temporayFile, type)
                .map(this::toResponse)
                .orElseGet(this::toFailResponse);
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
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private ResponseEntity<InputStreamResource> toFailResponse() {
        HttpHeaders headers = new HttpHeaders();
        byte[] bytes = "Error processing".getBytes(StandardCharsets.UTF_8);
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(bytes));
        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
