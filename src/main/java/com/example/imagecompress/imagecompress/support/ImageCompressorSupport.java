package com.example.imagecompress.imagecompress.support;

import com.example.imagecompress.imagecompress.TemporaryFileStorage;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCompressorSupport {
    public enum ImageFormat {
        GIF("gif"),
        JPEG("jpg"),
        PNG("png");
        public final String label;

        ImageFormat(String label) {
            this.label = label;
        }
    }

    private final TemporaryFileStorage temporaryFileStorage;
    private final CompressParamsFactory compressParamsFactory;
    private final DisposalMgr disposalMgr = new DisposalMgr();

    public ImageCompressorSupport(TemporaryFileStorage temporaryFileStorage, CompressParamsFactory compressParamsFactory) {
        this.temporaryFileStorage = temporaryFileStorage;
        this.compressParamsFactory = compressParamsFactory;
    }

    public File compressImage(BufferedImage originalImage, ImageFormat imageType) throws IOException {
        ImageToGrayscale.transformToGrayscale(originalImage);
        File compressedFile = temporaryFileStorage.createTemporayFile("." + imageType.label);
        ImageWriter writer = compressParamsFactory.getWriter();
        // Create an ImageOutputStream to write the compressed image
        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressedFile)) {
            // Set the output destination for the writer
            writer.setOutput(outputStream);

            // Write the image with the specified compression settings
            writer.write(null, new javax.imageio.IIOImage(originalImage, null, null),
                    compressParamsFactory.getImageWriteParam(writer));

            disposalMgr.register(compressedFile, writer);
        }
        return compressedFile;
    }
}
