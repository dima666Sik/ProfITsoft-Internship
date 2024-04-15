package ua.code.intership.proft.it.soft.service.parser;

import lombok.extern.log4j.Log4j2;
import ua.code.intership.proft.it.soft.model.attribute.PlanetAttribute;
import ua.code.intership.proft.it.soft.service.exception.FileProcessingException;
import ua.code.intership.proft.it.soft.service.generator.FileCreator;
import ua.code.intership.proft.it.soft.service.generator.XmlFileCreator;
import ua.code.intership.proft.it.soft.service.statistic.PlanetStatisticsProcessor;
import ua.code.intership.proft.it.soft.service.statistic.StatisticsProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static ua.code.intership.proft.it.soft.service.util.constant.FileConst.*;
import static ua.code.intership.proft.it.soft.service.util.generator.FileGenerator.generateDirectory;

/**
 * {@link JsonToXmlParser} is a class that implements the {@link FileParser} interface
 * and provides functionality to parse JSON files into XML format.
 * It processes JSON files from a specified directory,
 * converts them into XML file, and saves it to a default location.
 * <p> This class is Facade has simple methods that delegate hard work
 * to other services but to the client enough to start methods Facade class
 *
 * @see FileParser
 */
@Log4j2
public class JsonToXmlParser implements FileParser {
    public static final int DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES = 8;
    private final FileCreator fileCreator = new XmlFileCreator();
    private final StatisticsProcessor statisticsProcessor = PlanetStatisticsProcessor.getInstance();

    /**
     * Parses the JSON files located at the specified path,
     * converts them into XML format, and saves the result to the
     * specified statistic XML file by attribute.
     *
     * @param pathToJsonFiles the path to the directory containing JSON files to parse
     * @param attribute       the attribute that influence to generate statistic XML file
     * @return the XML file containing the parsed data
     * @throws IllegalArgumentException if the path to JSON files is null or empty, or if the attribute is invalid
     * and if the directory specified by pathToJsonFiles is not a valid directory or contains no files
     * @throws FileProcessingException  if an error occurs during file processing
     */
    @Override
    public File parse(String pathToJsonFiles, String attribute) {
        if (pathToJsonFiles == null || pathToJsonFiles.isEmpty())
            throw new IllegalArgumentException("Invalid path to files: " + pathToJsonFiles + ".\nPlease fill the path to files.");

        if (!isValidAttribute(attribute)) throw new IllegalArgumentException("Invalid attribute: " + attribute);

        log.info("The path to files: {} is valid and attribute: {} is exists", pathToJsonFiles, attribute);

        File directoryToJsonFiles = new File(pathToJsonFiles);

        if (!directoryToJsonFiles.isDirectory())
            throw new IllegalArgumentException("The file must be a directory, but it is not!");

        File[] files = directoryToJsonFiles.listFiles();

        if (files == null || files.length == 0)
            throw new IllegalArgumentException("Files absent or null! " +
                    "Choose another directory with files, or create file into it.");

        Path directoryToXmlFiles = Path.of(PATH_TO_XML_FILE_LIST);

        if (!Files.exists(directoryToXmlFiles)) generateDirectory(directoryToXmlFiles);

        ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES);
        CountDownLatch latch = new CountDownLatch(files.length);
        AtomicReference<Throwable> exceptionRef = new AtomicReference<>();

        for (File file : files) {
            executor.execute(() -> {
                try {
                    statisticsProcessor.collectStatistics(file, attribute);
                    log.info("File {} was read and analyzed.", file.getName());
                } catch (IOException e) {
                    log.error("File {} have problem with processing .json file: ", file.getName(), e);
                    exceptionRef.set(e);
                } finally {
                    latch.countDown();
                    log.info("Reducing latch count, current value is:{}", latch.getCount());
                }

            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting for a file to complete processing.", e);
            Thread.currentThread()
                  .interrupt();
        }

        executor.shutdown();

        Throwable exception = exceptionRef.get();
        if (exception != null) {
            throw new FileProcessingException("Error processing files!");
        }

        return fileCreator.generate(PATH_TO_XML_FILE_LIST, attribute);
    }

    /**
     * Checks if the specified attribute is valid.
     *
     * @param attribute the attribute to validate
     * @return true if the attribute is valid, false otherwise
     */
    private boolean isValidAttribute(String attribute) {
        return PlanetAttribute.isAttributeExist(attribute);
    }

}
