package ua.code.intership.proft.it.soft.service.parser;

import java.io.File;
@FunctionalInterface
public interface FileParser {
    File parse(String pathToJsonFiles, String pathToXmlFile, String attribute);
}
