package ua.code.intership.proft.it.soft.service.parser;

import ua.code.intership.proft.it.soft.model.attribute.CoreAttribute;
import ua.code.intership.proft.it.soft.service.generator.FileGenerator;
import ua.code.intership.proft.it.soft.service.generator.XmlFileCreator;

import java.io.File;

public class JsonToXmlParser implements FileParser {
    private static final int DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES = 10;
    private final FileGenerator<File> fileGenerator = new XmlFileCreator<>();
    /*
     * 1 Going through the foreach +
     * 1.2 Processing the file +
     * ---
     * 1.2.1 Reading the file by chunks +
     * 1.2.2 Processing the statistics info from the file chunks +
     * ---
     * 1.2.3 After work all parallel processing files we will get statistics +
     * 1.3 Generate xml based on the statistics +
     * 1.4 Returning the statistics_by_{attribute}.xml file with statistics by all files +
     */
    @Override
    public File parse(String pathToJsonFiles, String pathToXmlFile, String attribute) {
        if (pathToJsonFiles == null || pathToJsonFiles.isEmpty())
            throw new IllegalArgumentException("Invalid path to files: " + pathToJsonFiles + ".\nPlease fill the path to files.");

        if (!isValidAttribute(attribute)) throw new IllegalArgumentException("Invalid attribute: " + attribute);

        File directory = new File(pathToJsonFiles);

        if (!directory.isDirectory())
            throw new IllegalArgumentException("The file must be a directory, but it is not!");

        File[] files = directory.listFiles();

        if (files == null || files.length == 0)
            throw new IllegalArgumentException("Files does not exist: " + pathToJsonFiles + ".");

        return fileGenerator.generate(pathToXmlFile, files, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES, attribute);
    }

    private boolean isValidAttribute(String attribute) {
        return CoreAttribute.isAttributeExist(attribute);
    }

}
