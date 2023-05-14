package com.example.imagecompress;

import com.example.imagecompress.support.ImageCompressService;
import org.apache.commons.io.FilenameUtils;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@State(Scope.Benchmark)
public class SimpleBenchmark {
    private AnnotationConfigApplicationContext context;
    private ImageCompressService imageCompressService;

    @Setup(Level.Trial)
    public void doSetup() {
        context = new AnnotationConfigApplicationContext();
        context.scan("com.example.imagecompress");
        context.refresh();
        imageCompressService = context.getBean(ImageCompressService.class);
    }

    @Benchmark
    public void simpleTest() throws IOException {
        Path sample = Paths.get("sample");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sample)) {
            for (Path path : stream) {
                String extension = FilenameUtils.getExtension(path.toString());
                imageCompressService.compress(path.toFile(), extension);
            }
        }
    }
}
