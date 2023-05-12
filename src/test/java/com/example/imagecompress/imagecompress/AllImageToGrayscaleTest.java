package com.example.imagecompress.imagecompress;

import com.example.imagecompress.imagecompress.support.ImageFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

class AllImageToGrayscaleTest {
    private static final Logger logger = LoggerFactory.getLogger(AllImageToGrayscaleTest.class);
    private static ImageToGrayscale2 imageToGrayscale;

    @BeforeAll
    static void init() throws IOException {
        TemporaryFileStorage temporaryFileStorage = getTemporaryFileStorage();
        imageToGrayscale = new ImageToGrayscale2(temporaryFileStorage);
    }
    private static Stream<Arguments> samples() throws IOException {
        return Stream.of(
                Arguments.of("sample/input.jpg", ImageFormat.JPEG),
                Arguments.of("sample/input.png", ImageFormat.PNG),
                Arguments.of("sample/input.gif", ImageFormat.GIF)
        );
    }

    @ParameterizedTest
    @MethodSource("samples")
    void simpleTest(String input, ImageFormat imageFormat) throws Exception {
        logger.debug("Testing for {}", input);
        File file = Paths.get(input).toFile();
        File compressed = imageToGrayscale.transformToGrayscale(file, imageFormat);
        float compressedPercentage = TestSupport.getCompressedPercentage(file, compressed);
        Assertions.assertTrue(compressedPercentage < 100);
    }
}
