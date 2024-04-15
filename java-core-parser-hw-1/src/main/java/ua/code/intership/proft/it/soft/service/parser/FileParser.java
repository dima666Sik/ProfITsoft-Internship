package ua.code.intership.proft.it.soft.service.parser;

import java.io.File;
/**
 * A functional interface that defines the contract to parse a file.
 */
@FunctionalInterface
public interface FileParser {
    /**
     * Parses the specified file and returns the parsed file.
     *
     * @param pathToJsonFiles the path to the directory containing the JSON files
     * @param attribute       the name of the attribute to be extracted from the XML file
     * @return the parsed file
     */
    File parse(String pathToJsonFiles, String attribute);
}
