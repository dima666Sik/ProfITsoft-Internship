package ua.code.intership.proft.it.soft.service.generator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.service.exception.FileGenerationException;
import ua.code.intership.proft.it.soft.service.util.TimeChecker;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.code.intership.proft.it.soft.service.util.FileProcessor.clearFilesByDirectory;
import static ua.code.intership.proft.it.soft.ui.util.constant.PlanetGeneratorConst.PLANET_LISTS;
import static ua.code.intership.proft.it.soft.service.util.constant.ParserConst.DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES;
import static ua.code.intership.proft.it.soft.service.util.constant.FileTestConst.*;

class JsonObjectMultipleFileGeneratorTest {
    private ObjectMultipleFileGenerator<Planet> objectMultipleFileGenerator;

    @BeforeEach
    void setUp() {
        objectMultipleFileGenerator = new JsonObjectMultipleFileGenerator<>();
    }

    @AfterEach
    void tearDown() {
        clearFilesByDirectory(PATH_TO_TEST_JSON_FILE_LIST);
    }

    @Test
    void testGenerateJSONFilesShouldWithoutThrow() {
        TimeChecker.timeTest(() -> Assertions.assertDoesNotThrow(() ->
                objectMultipleFileGenerator.generate(PATH_TO_TEST_JSON_FILE_LIST, PLANET_LISTS, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES)));
    }

    @Test
    void testGenerateJSONFilesShouldExistFiles() {
        TimeChecker.timeTest(() ->
                objectMultipleFileGenerator.generate(PATH_TO_TEST_JSON_FILE_LIST, PLANET_LISTS, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES));
        File[] files = new File(PATH_TO_TEST_JSON_FILE_LIST).listFiles();

        assertNotNull(files, "files should not be null!");
        assertNotNull(PLANET_LISTS, "PLANET_LISTS should not be null!");

        assertEquals(files.length, PLANET_LISTS.size());
    }

    @Test
    void testGenerateXmlStatisticsFileShouldThrowByNullData() {
        Throwable exception =  assertThrows(IllegalArgumentException.class, () ->
                objectMultipleFileGenerator.generate(PATH_TO_TEST_JSON_FILE_LIST, null, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES));
        assertEquals("The list of objects is empty!", exception.getMessage());
    }

    @Test
    void testGenerateXmlStatisticsFileShouldThrowByInvalidCountThreads() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                objectMultipleFileGenerator.generate(PATH_TO_TEST_JSON_FILE_LIST, PLANET_LISTS, 0));
        assertEquals("Count threads must be greater than zero! " +
                "Please select right count threads to thread pool.", exception.getMessage());
    }
}