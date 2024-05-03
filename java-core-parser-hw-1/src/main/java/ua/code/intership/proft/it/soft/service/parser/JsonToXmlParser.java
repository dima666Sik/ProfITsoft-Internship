package ua.code.intership.proft.it.soft.service.parser;

import lombok.extern.log4j.Log4j2;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.service.exception.FileGenerationException;
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
import java.util.concurrent.atomic.AtomicReferenceArray;

import static ua.code.intership.proft.it.soft.service.util.FileProcessor.generateDirectory;
import static ua.code.intership.proft.it.soft.service.util.FileProcessor.getFiles;
import static ua.code.intership.proft.it.soft.service.util.constant.FileConst.PATH_TO_XML_FILE_LIST;

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
    public static final int DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES = 10;
    public static final String JSON_FILE_EXTENSION = ".json";
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
     *                                  and if the directory specified by pathToJsonFiles is not a valid directory or contains no files
     * @throws FileProcessingException  if an error occurs during file processing
     */
    @Override
    public File parse(String pathToJsonFiles, String attribute) {
        validateInputParameters(pathToJsonFiles, attribute);

        File[] jsonFiles = getFiles(pathToJsonFiles, JSON_FILE_EXTENSION);

        processFilesByThreads(attribute, jsonFiles);

        Path directoryToXmlFiles = Path.of(PATH_TO_XML_FILE_LIST);

        if (!Files.exists(directoryToXmlFiles)) generateDirectory(directoryToXmlFiles);

        return fileCreator.generate(PATH_TO_XML_FILE_LIST, attribute);
    }

    /**
     * Validates the input parameters for the parser.
     *
     * @param pathToJsonFiles the path to the directory containing JSON files
     * @param attribute       the attribute used for conversion
     * @throws IllegalArgumentException if the path to files is invalid or empty, or if the attribute is invalid
     */
    private void validateInputParameters(String pathToJsonFiles, String attribute) {
        if (pathToJsonFiles == null || pathToJsonFiles.isEmpty())
            throw new IllegalArgumentException("Invalid path to files: " + pathToJsonFiles + ".\nPlease fill the path to files.");

        log.info("The path to files: {} is valid and attribute: {} is exists", pathToJsonFiles, attribute);
    }

    /**
     * Processes the specified JSON files by threads, collect statistics data from them.
     *
     * @param attribute the attribute that influence to generate statistic XML file
     * @param jsonFiles the JSON files to process
     */
    private void processFilesByThreads(String attribute, File[] jsonFiles) {
        ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES);
        CountDownLatch latch = new CountDownLatch(jsonFiles.length);
        AtomicReferenceArray<Throwable> exceptionRef = new AtomicReferenceArray<>(jsonFiles.length);

        for (int i = 0; i < jsonFiles.length; i++) {
            final int outerIndex = i;
            executor.execute(() -> {
                File file = jsonFiles[outerIndex];
                try {
                    statisticsProcessor.collectStatistics(file, attribute);
                    log.info("File {} was read and analyzed.", file.getName());
                } catch (IOException e) {
                    log.error("File {} have problem with processing .json file: ", file.getName(), e);
                    exceptionRef.set(outerIndex, e);
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

        for (int i = 0; i < exceptionRef.length(); i++) {
            Throwable exception = exceptionRef.get(i);
            if (exception != null) {
                throw new FileGenerationException("Error generating files!", exception);
            }
        }
    }

}
