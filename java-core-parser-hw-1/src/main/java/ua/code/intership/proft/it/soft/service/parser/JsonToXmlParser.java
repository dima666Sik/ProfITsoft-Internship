package ua.code.intership.proft.it.soft.service.parser;

import lombok.extern.log4j.Log4j2;
import ua.code.intership.proft.it.soft.model.attribute.PlanetAttribute;
import ua.code.intership.proft.it.soft.service.generator.FileCreator;
import ua.code.intership.proft.it.soft.service.generator.XmlFileCreator;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * {@link JsonToXmlParser} is a class that implements the {@link FileParser} interface
 * and provides functionality to parse JSON files into XML format.
 * It processes JSON files from a specified directory,
 * converts them into XML files, and saves them to a designated location.
 * <p> This class is Facade has simple methods that delegate hard work
 * to other services but to the client enough to start methods Facade class
 *
 * @see FileParser
 */
@Log4j2
public class JsonToXmlParser implements FileParser {
    private static final int DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES = 10;
    private final FileCreator<File> fileCreator = new XmlFileCreator<>();

    /**
     * Parses the JSON files located at the specified path,
     * converts them into XML format, and saves the result to the specified statistic XML file by attribute.
     *
     * @param pathToJsonFiles the path to the directory containing JSON files to parse
     * @param pathToXmlFile   the path to the XML file where the parsed data will be saved
     * @param attribute       the attribute that influence to generate statistic XML file
     * @return the XML file containing the parsed data
     * @throws IllegalArgumentException if the pathToJsonFiles is null, empty, or invalid,
     *                                  or if the attribute is invalid
     */
    @Override
    public File parse(String pathToJsonFiles, String pathToXmlFile, String attribute) {
        if (pathToJsonFiles == null || pathToJsonFiles.isEmpty())
            throw new IllegalArgumentException("Invalid path to files: " + pathToJsonFiles + ".\nPlease fill the path to files.");

        if (!isValidAttribute(attribute)) throw new IllegalArgumentException("Invalid attribute: " + attribute);

        log.info("The path to files: {} is valid and attribute: {} is exists", pathToJsonFiles, attribute);

        File directory = new File(pathToJsonFiles);

        if (!directory.isDirectory())
            throw new IllegalArgumentException("The file must be a directory, but it is not!");

        File[] files = directory.listFiles();

        if (files == null)
            throw new IllegalArgumentException("The files should not be null!");

        List<File> fileList = Arrays.stream(files)
                                     .toList();

        return fileCreator.generate(pathToXmlFile, fileList, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES, attribute);
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
