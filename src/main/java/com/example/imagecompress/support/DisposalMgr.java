package com.example.imagecompress.support;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageWriter;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * To do the resources clean up out of band from the calling thread and avoid file locking issues on windows
 */
public class DisposalMgr {
    private final static Logger logger = LoggerFactory.getLogger(DisposalMgr.class);
    private final Cache<String, ImageWriter> cache = Caffeine.newBuilder()
            .scheduler(Scheduler.systemScheduler())
            .expireAfterWrite(250, TimeUnit.MILLISECONDS)
            .evictionListener((String key, ImageWriter writer, RemovalCause cause) -> {
                if (writer != null) {
                    writer.dispose();
                    logger.info("Disposing key=[{}] item=[{}]", key, cause);
                }
            })
            .build();

    public void register(File file, ImageWriter imageWriter) {
        cache.put(file.getAbsolutePath(), imageWriter);
        logger.debug("Pushed item into stack now have [{}] items", cache.asMap().size());
    }

}
