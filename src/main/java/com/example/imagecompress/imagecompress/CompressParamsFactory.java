package com.example.imagecompress.imagecompress;

import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public interface CompressParamsFactory {
    ImageWriter getWriter();
    ImageWriteParam getImageWriteParam(ImageWriter writer);
}
