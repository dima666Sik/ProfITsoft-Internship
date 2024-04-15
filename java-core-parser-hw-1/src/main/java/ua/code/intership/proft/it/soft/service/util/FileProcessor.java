package ua.code.intership.proft.it.soft.service.util;

import lombok.extern.log4j.Log4j2;
import ua.code.intership.proft.it.soft.service.exception.FileGenerationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public final class FileProcessor {
    /**
     * Clears all files in the specified directory.
     *
     * @param path the path to the directory containing the files to be cleared
     */
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
    /**
     * Generates a directory at the specified path.
     *
     * @param pathToDirectory the path to the directory to be generated
     * @throws FileGenerationException if an error occurs during directory generation
     */
    public static void generateDirectory(Path pathToDirectory) {
        try {
            Files.createDirectories(pathToDirectory);
            log.info("Directory created successfully: {}", pathToDirectory);
        } catch (IOException e) {
            log.error("Error auto creating directory: {}", e.getMessage());
            throw new FileGenerationException("Error generating directory: " + pathToDirectory);
        }
    }

    /**
     * Retrieves the JSON files from the specified directory and extension
     *
     * @param pathToJsonFiles the path to the directory containing JSON files
     * @param extension the extension of the files that we want to retrieve
     * @return an array of File objects representing the JSON files
     * @throws IllegalArgumentException if the specified path is not a directory, if the directory is empty,
     *                                  or if there are no JSON files in the directory
     */
    public static File[] getFiles(String pathToJsonFiles, String extension){
        File directoryToJsonFiles = new File(pathToJsonFiles);

        if (!directoryToJsonFiles.isDirectory())
            throw new IllegalArgumentException("The file must be a directory, but it is not!");

        File[] allFilesFromDirectory = directoryToJsonFiles.listFiles();

        if (allFilesFromDirectory == null)
            throw new IllegalArgumentException("Array with list files can not be null!");

        File[] jsonFiles = Arrays.stream(allFilesFromDirectory)
                                 .filter(file -> file.getName()
                                                     .endsWith(extension))
                                 .toArray(File[]::new);

        if (jsonFiles.length == 0)
            throw new IllegalArgumentException("Files absent! " +
                    "Choose another directory with files, or create file into it.");

        return jsonFiles;
    }
}
