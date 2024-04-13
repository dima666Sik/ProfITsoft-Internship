package ua.code.intership.proft.it.soft.service.parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.model.attribute.PlanetAttribute;
import ua.code.intership.proft.it.soft.service.generator.JsonObjectMultipleFileGenerator;
import ua.code.intership.proft.it.soft.service.generator.ObjectMultipleFileGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static ua.code.intership.proft.it.soft.service.util.FileProcessor.clearFilesByDirectory;
import static ua.code.intership.proft.it.soft.service.util.constant.ConstantGenerator.DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES;
import static ua.code.intership.proft.it.soft.service.util.constant.FileConstant.*;

class JsonToXmlParserTest {
    private FileParser fileParser;

    @BeforeEach
    void setUp() {
        fileParser = new JsonToXmlParser();
        ObjectMultipleFileGenerator<Planet> objectMultipleFileGenerator = new JsonObjectMultipleFileGenerator<>();

        objectMultipleFileGenerator.generate(PATH_TO_JSON_FILE_LIST, PLANET_LISTS, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES);
    }

    @AfterEach
    void tearDown() {
        clearFilesByDirectory(PATH_TO_JSON_FILE_LIST);
        clearFilesByDirectory(PATH_TO_XML_FILE_LIST);
    }

    @Test
    void testParseShouldWithoutThrow() {
        assertDoesNotThrow(() ->
                fileParser.parse(PATH_TO_JSON_FILE_LIST, PATH_TO_XML_FILE_LIST, PlanetAttribute.ATMOSPHERIC_COMPOSITION));
    }

    @Test
    void testParseShouldThrowWithoutPathToJsonFileList() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                fileParser.parse(null, PATH_TO_XML_FILE_LIST, PlanetAttribute.ATMOSPHERIC_COMPOSITION));
        assertEquals("Invalid path to files: " + null + ".\nPlease fill the path to files.", exception.getMessage());
    }

    @Test
    void testParseShouldThrowWithoutValidAttribute() {
        String attribute = "unknownAttribute";
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                fileParser.parse(PATH_TO_JSON_FILE_LIST, PATH_TO_XML_FILE_LIST, attribute));
        assertEquals("Invalid attribute: " + attribute, exception.getMessage());
    }

    @Test
    void testParseShouldThrowWithoutPathToDirectory() {
        String path = FILE_JSON_LIST.get(0)
                                    .getPath();
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                fileParser.parse(path, PATH_TO_XML_FILE_LIST, PlanetAttribute.ATMOSPHERIC_COMPOSITION));
        assertEquals("The file must be a directory, but it is not!", exception.getMessage());
    }
}