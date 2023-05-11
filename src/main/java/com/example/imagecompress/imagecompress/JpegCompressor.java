package com.example.imagecompress.imagecompress;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;

public class JpegCompressor {
  private final ImageCompressorSupport imageCompressorSupport;

  public JpegCompressor(TemporaryFileStorage temporaryFileStorage) {
    this.imageCompressorSupport = new ImageCompressorSupport(temporaryFileStorage);
  }

  public void main() throws Exception {
    // Load the original image
    BufferedImage image = ImageIO.read(new File("input.jpg"));

    // Get the JPEG image writer
    ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
    ImageWriteParam param = buildImageWriteParam(writer);


    imageCompressorSupport.extracted(image, writer, param, ImageCompressorSupport.ImageFormat.JPEG);
    writer.dispose();
  }

  private static ImageWriteParam buildImageWriteParam(ImageWriter writer) {
    // Set the compression quality to 0.5
    ImageWriteParam param = writer.getDefaultWriteParam();
    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    param.setCompressionQuality(0.5f);
    return param;
  }
}
