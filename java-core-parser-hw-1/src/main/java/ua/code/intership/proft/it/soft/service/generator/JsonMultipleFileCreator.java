package ua.code.intership.proft.it.soft.service.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class JsonMultipleFileCreator<T> implements MultipleFileGenerator<T> {
    @Override
    public void generate(String pathToJsonDirectory, List<List<T>> listObjectsList, int countThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(countThreads);
        CountDownLatch latch = new CountDownLatch(listObjectsList.size());

        for (int i = 0; i < listObjectsList.size(); i++) {
            final int outerIndex = i;

            executor.execute(() -> {
                File file = new File(pathToJsonDirectory + File.separator + "planet" + outerIndex + ".json");
                log.info("File {} was generated.", file.getName());
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    objectMapper.writeValue(file, listObjectsList.get(outerIndex));
                    log.info("JSON data for file:{} was generated.", file.getName());
                } catch (IOException e) {
                    log.error("Generating files with .json format was not successful.", e);
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting for tasks to complete.", e);
            Thread.currentThread()
                  .interrupt();
        }

        executor.shutdown();
    }


}
