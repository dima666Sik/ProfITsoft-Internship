package ua.code.intership.proft.it.soft.service.generator;

import lombok.extern.log4j.Log4j2;
import ua.code.intership.proft.it.soft.service.exception.FileGenerationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReferenceArray;

import static ua.code.intership.proft.it.soft.service.util.FileProcessor.*;
import static ua.code.intership.proft.it.soft.service.util.constant.JacksonConst.OBJECT_MAPPER;

/**
 * JsonObjectMultipleFileGenerator is a class that implements the ObjectMultipleFileGenerator interface.
 * It provides functionality to generate multiple JSON files from lists of objects.
 *
 * @param <T> the type of objects to generate JSON files for
 * @see ObjectMultipleFileGenerator
 */
@Log4j2
public class JsonObjectMultipleFileGenerator<T> implements ObjectMultipleFileGenerator<T> {
    /**
     * Generates JSON files for lists of objects in parallel.
     * Each list of objects will be written to a separate JSON file.
     *
     * @param pathToJsonDirectory the directory where the JSON files will be generated
     * @param listObjectsList     a list containing lists of objects to generate JSON files for
     * @param countThreads        the number of threads to use for parallel generation
     * @throws IllegalArgumentException if the list of objects is null or empty,
     *                                  or if countThreads is less than or equal to zero
     */
    @Override
    public void generate(String pathToJsonDirectory, List<List<T>> listObjectsList, int countThreads) {
        validateInputParameter(pathToJsonDirectory, listObjectsList, countThreads);

        ExecutorService executor = Executors.newFixedThreadPool(countThreads);
        CountDownLatch latch = new CountDownLatch(listObjectsList.size());
        AtomicReferenceArray<Throwable> exceptionRef = new AtomicReferenceArray<>(listObjectsList.size());

        for (int i = 0; i < listObjectsList.size(); i++) {
            final int outerIndex = i;

            executor.execute(() -> {
                File file = new File(pathToJsonDirectory + File.separator + "planet" + outerIndex + ".json");
                try {
                    OBJECT_MAPPER.writeValue(file, listObjectsList.get(outerIndex));
                    log.info("JSON data for file:{} was generated.", file.getName());
                } catch (IOException e) {
                    log.error("Generating file:{} with .json format was not successful.", file.getName(), e);
                    exceptionRef.set(outerIndex, e);
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

        for (int i = 0; i < exceptionRef.length(); i++) {
            Throwable exception = exceptionRef.get(i);
            if (exception != null) {
                throw new FileGenerationException("Error generating files!", exception);
            }
        }
    }

    /**
     * Validates the input parameters.
     *
     * @param pathToJsonDirectory the directory where the JSON files will be generated
     * @param listObjectsList     a list containing lists of objects to generate JSON files for
     * @param countThreads        the number of threads to use for parallel generation
     * @throws IllegalArgumentException if the list of objects is null or empty,
     *                                  or if countThreads is less than or equal to zero
     */
    private void validateInputParameter(String pathToJsonDirectory, List<List<T>> listObjectsList, int countThreads) {
        Path directory = Path.of(pathToJsonDirectory);

        if (!Files.exists(directory)) generateDirectory(directory);

        if (listObjectsList == null || listObjectsList.isEmpty()) {
            log.error("The list of objects is empty! Cannot proceed.");
            throw new IllegalArgumentException("The list of objects is empty!");
        }

        if (countThreads <= 0) {
            log.error("Invalid count of threads: {}. Must be greater than zero.", countThreads);
            throw new IllegalArgumentException("Count threads must be greater than zero! " +
                    "Please select right count threads to thread pool.");
        }
    }

}
