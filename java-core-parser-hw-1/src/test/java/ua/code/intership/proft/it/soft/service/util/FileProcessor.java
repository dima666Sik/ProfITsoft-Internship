package ua.code.intership.proft.it.soft.service.util;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public final class FileProcessor {
    public static void clearFilesByDirectory(String path) {
        File[] files = new File(path).listFiles();
        if (files != null && files.length > 0) {

            ExecutorService executor = Executors.newFixedThreadPool(files.length);
            CountDownLatch latch = new CountDownLatch(files.length);

            for (File file : files) {
                executor.execute(() -> {
                    try {
                        log.info(file.delete()
                                ? "The file: " + file.getName() + " was deleted successfully!"
                                : "Deleting the file: " + file.getName() + "was failed!");
                    } finally {
                        latch.countDown();
                    }
                });
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread()
                      .interrupt();
            }

            executor.shutdown();
        }
    }
}
