package com.example.imagecompress.imagecompress;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;

public class JpegCompressor {
  private final Support support;

  public JpegCompressor(Support support) {
    this.support = support;
  }

  public void main() throws Exception {
    // Load the original image
    BufferedImage image = ImageIO.read(new File("input.jpg"));

    // Get the JPEG image writer
    ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
    ImageWriteParam param = buildImageWriteParam(writer);

    File compressedFile = new File("build/output.jpg");
    support.extracted(image, writer, param, compressedFile);
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
