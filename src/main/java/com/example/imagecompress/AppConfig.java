package com.example.imagecompress;

import com.example.imagecompress.image.GifCompressor;
import com.example.imagecompress.image.JpegCompressor;
import com.example.imagecompress.image.PngCompressor;
import com.example.imagecompress.image.TemporaryFileStorage;
import com.example.imagecompress.support.TemporaryFileStorageFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

@Configuration
@EnableScheduling
public class AppConfig {
    private Duration maxWaitDuration = Duration.ofSeconds(10);
    @Bean
    public TemporaryFileStorage temporaryFileStorage() throws IOException {
        File baseDirectory = TemporaryFileStorageFactory.getBaseDirectory();
        return TemporaryFileStorageFactory.createTemporaryFileStorage(baseDirectory, maxWaitDuration);
    }
    @Bean
    public JpegCompressor jpegCompressor(TemporaryFileStorage temporaryFileStorage) {
        return new JpegCompressor(temporaryFileStorage);
    }

    @Bean
    public PngCompressor pngCompressor(TemporaryFileStorage temporaryFileStorage) {
        return new PngCompressor(temporaryFileStorage);
    }
    @Bean
    public GifCompressor gifCompressor(TemporaryFileStorage temporaryFileStorage) {
        return new GifCompressor(temporaryFileStorage);
    }
}
