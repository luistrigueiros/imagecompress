package com.example.imagecompress.imagecompress.support;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageWriter;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class DisposalMgr {
    private final static Logger logger = LoggerFactory.getLogger(DisposalMgr.class);
    private final Cache<String, ImageWriter> cache = Caffeine.newBuilder()
            .expireAfterWrite(250, TimeUnit.MILLISECONDS)
            .evictionListener((String key, ImageWriter writer, RemovalCause cause) -> {
                if (writer != null) {
                    writer.dispose();
                    logger.debug("Disposing key=[{}] item=[{}]", key, cause);
                }
            })
            .build();

    public void register(File file, ImageWriter imageWriter) {
        cache.put(file.getAbsolutePath(), imageWriter);
        logger.debug("Pushed item into stack now have [{}] items", cache.asMap().size());
    }

}
