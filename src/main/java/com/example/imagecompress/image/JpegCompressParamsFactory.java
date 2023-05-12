package com.example.imagecompress.image;

import com.example.imagecompress.support.CompressParamsFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

class JpegCompressParamsFactory implements CompressParamsFactory {
    @Override
    public ImageWriter getWriter() {
        return ImageIO.getImageWritersByFormatName("jpeg").next();
    }

    @Override
    public ImageWriteParam getImageWriteParam(ImageWriter writer) {
        return buildImageWriteParam(writer);
    }

    private ImageWriteParam buildImageWriteParam(ImageWriter writer) {
        ImageWriteParam param = new JPEGImageWriteParam(null);
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.3f);
        return param;
    }
}
