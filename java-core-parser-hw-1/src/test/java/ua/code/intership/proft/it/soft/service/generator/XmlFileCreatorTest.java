package ua.code.intership.proft.it.soft.service.generator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.model.attribute.PlanetAttribute;
import ua.code.intership.proft.it.soft.service.statistic.StatisticsProcessor;
import ua.code.intership.proft.it.soft.service.util.reflect.ClassMetadataProvider;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.code.intership.proft.it.soft.service.util.FileProcessor.clearFilesByDirectory;
import static ua.code.intership.proft.it.soft.service.util.constant.ConstantGenerator.DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES;
import static ua.code.intership.proft.it.soft.service.util.constant.ConstantGenerator.DEFAULT_STATISTICS_INFO_SET;
import static ua.code.intership.proft.it.soft.service.util.constant.FileConstant.*;
import static ua.code.intership.proft.it.soft.service.util.constant.FileConstant.PLANET_LISTS;

@ExtendWith(MockitoExtension.class)
class XmlFileCreatorTest {
    private FileCreator<File> fileCreator;
    private List<File> fileList;
    @Mock private StatisticsProcessor statisticsProcessor;

    @BeforeEach
    void setUp() {
        fileCreator = new XmlFileCreator<>();
        ObjectMultipleFileGenerator<Planet> objectMultipleFileGenerator = new JsonObjectMultipleFileGenerator<>();

        objectMultipleFileGenerator.generate(PATH_TO_JSON_FILE_LIST, PLANET_LISTS, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES);
        File[] files = new File(PATH_TO_JSON_FILE_LIST).listFiles();

        assertNotNull(files, "files should not be null!");
        assertNotNull(PLANET_LISTS, "PLANET_LISTS should not be null!");

        assertEquals(files.length, PLANET_LISTS.size());

        fileList = Arrays.stream(files).toList();
    }

    @AfterEach
    void tearDown() {
        clearFilesByDirectory(PATH_TO_JSON_FILE_LIST);
        clearFilesByDirectory(PATH_TO_XML_FILE_LIST);
    }

    @Test
    void testGenerateXmlStatisticsFileShouldWithoutThrow() {
        assertDoesNotThrow(() -> {
            Mockito.when(statisticsProcessor.getStatisticsSortedSet(Mockito.any()))
                   .thenReturn(DEFAULT_STATISTICS_INFO_SET);
            ClassMetadataProvider.reflectInitMockStateForService(fileCreator, "statisticsProcessor", statisticsProcessor);
            fileCreator.generate(PATH_TO_XML_FILE_LIST, fileList, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES, PlanetAttribute.ATMOSPHERIC_COMPOSITION);
        });
    }

    @Test
    void testGenerateXmlStatisticsFileShouldThrowByNullData() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                fileCreator.generate(PATH_TO_XML_FILE_LIST, null, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES, PlanetAttribute.ATMOSPHERIC_COMPOSITION));
        assertEquals("Files list is empty or null! " +
                "Choose another directory with files, or create file into it.", exception.getMessage());
    }

    @Test
    void testGenerateXmlStatisticsFileShouldThrowByInvalidCountThreads() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                fileCreator.generate(PATH_TO_XML_FILE_LIST, fileList, 0, PlanetAttribute.ATMOSPHERIC_COMPOSITION));
        assertEquals("Count threads must be greater than zero! " +
                "Please select right count threads to thread pool.", exception.getMessage());
    }
}