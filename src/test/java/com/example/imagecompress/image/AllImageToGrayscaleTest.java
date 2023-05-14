package com.example.imagecompress.image;

import com.example.imagecompress.support.ImageFormat;
import com.example.imagecompress.support.Util;
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

import static com.example.imagecompress.image.TestSupport.getTemporaryFileStorage;

class AllImageToGrayscaleTest {
    private static final Logger logger = LoggerFactory.getLogger(AllImageToGrayscaleTest.class);
    private static ImageGrayscaler imageToGrayscale;

    @BeforeAll
    static void init() throws IOException {
        TemporaryFileStorage temporaryFileStorage = getTemporaryFileStorage();
        imageToGrayscale = new ImageToGrayscale3(temporaryFileStorage);
    }
    private static Stream<Arguments> samples()  {
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
        float compressedPercentage = Util.getCompressedPercentage(file, compressed, true);
        Assertions.assertTrue(compressedPercentage < 100);
    }
}
