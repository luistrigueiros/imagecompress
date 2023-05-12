package com.example.imagecompress.imagecompress.support;

public enum ImageFormat {
    GIF("gif"),
    JPEG("jpg"),
    PNG("png");
    public final String label;

    ImageFormat(String label) {
        this.label = label;
    }
}
