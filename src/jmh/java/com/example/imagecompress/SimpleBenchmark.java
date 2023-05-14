package com.example.imagecompress;

import com.example.imagecompress.support.Util;
import org.apache.commons.io.FilenameUtils;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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
                File file = path.toFile();
                Optional<File> compress = imageCompressService.compress(file, extension);
                logResult(file, compress);
            }
        }
    }

    private void logResult(File input, Optional<File> optionalFile) {
        optionalFile.ifPresent( output -> {
            float compressedPercentage = Util.getCompressedPercentage(input, output);
            String msg = String.format("Compressed file=%s percentage=%f", input.getAbsolutePath(), compressedPercentage);
            System.out.println(msg);
        });
    }
}
