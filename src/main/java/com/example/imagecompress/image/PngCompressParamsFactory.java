package com.example.imagecompress.image;

import com.example.imagecompress.support.CompressParamsFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

class PngCompressParamsFactory implements CompressParamsFactory {

    @Override
    public ImageWriter getWriter() {
        return ImageIO.getImageWritersByFormatName("png").next();
    }

    @Override
    public ImageWriteParam getImageWriteParam(ImageWriter writer) {
        return buildImageWriteParam(writer);
    }

    private ImageWriteParam buildImageWriteParam(ImageWriter writer) {
        // Create an ImageWriteParam to specify compression settings
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        // Set the compression mode to MODE_EXPLICIT and the compression type
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionType("Deflate");
        // Set the compression quality (0.0 to 1.0, where 1.0 is highest quality)
        writeParam.setCompressionQuality(0.1f);
        return writeParam;
    }
}
