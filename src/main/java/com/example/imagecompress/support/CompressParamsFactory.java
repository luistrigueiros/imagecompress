package com.example.imagecompress.support;

import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public interface CompressParamsFactory {
    ImageWriter getWriter();
    ImageWriteParam getImageWriteParam(ImageWriter writer);
}
