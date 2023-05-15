package com.example.imagecompress.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * To do the resources clean up out of band from the calling thread and avoid file locking issues on windows
 */
public class DisposalMgr {
    private final static Logger logger = LoggerFactory.getLogger(DisposalMgr.class);
    private final BlockingQueue<ImageWriter> blockingQueue = new LinkedBlockingQueue<>();

    private static DisposalMgr instance = null;

    private DisposalMgr() {
        Thread thread = new Thread(() -> {
            while (true) {
                processRequest();
            }
        }, "DisposalMgrThread");
        logger.info("Starting processing thread now");
        thread.start();
    }

    public static DisposalMgr getInstance() {
        if (instance == null) {
            instance = new DisposalMgr();
        }
        return instance;
    }

    public void register(ImageWriter imageWriter) {
        blockingQueue.add(imageWriter);
        logger.debug("Registered writer=[{}] , now have [{}] items", imageWriter.hashCode(), blockingQueue.size());
    }

    private void processRequest() {
        try {
            ImageWriter writer = blockingQueue.take();
            logger.debug("Performing disposal of writer={}", writer.hashCode());
            writer.dispose();
        } catch (Throwable throwable) {
            logger.error("Error while disposing writer ", throwable);
        }
    }
}
