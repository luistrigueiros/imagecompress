package com.example.imagecompress.imagecompress;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.example.imagecompress.imagecompress.TestSupport.getTemporaryFileStorage;

class AllImageCompressorTest {
    private static final Logger logger = LoggerFactory.getLogger(AllImageCompressorTest.class);

    private static Stream<Arguments> samples() throws IOException {
        TemporaryFileStorage temporaryFileStorage = getTemporaryFileStorage();
        return Stream.of(
                Arguments.of("sample/input.jpg", new JpegCompressor(temporaryFileStorage)),
                Arguments.of("sample/input.png", new PngCompressor(temporaryFileStorage)),
                Arguments.of("sample/input.gif", new GifCompressor(temporaryFileStorage))
        );
    }

    @ParameterizedTest
    @MethodSource("samples")
    void simpleTest(String input, ImageCompressor imageCompressor) throws Exception {
        logger.debug("Testing for {}", input);
        File file = Paths.get(input).toFile();
        File compressed = imageCompressor.compressImage(file);
        float compressedPercentage = TestSupport.getCompressedPercentage(file, compressed);
        Assertions.assertTrue(compressedPercentage < 100);
    }
}