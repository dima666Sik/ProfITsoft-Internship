package ua.code.intership.proft.it.soft.service.generator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.service.exception.FileGenerationException;
import ua.code.intership.proft.it.soft.service.statistic.StatisticsProcessor;
import ua.code.intership.proft.it.soft.service.util.reflect.ClassMetadataProvider;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static ua.code.intership.proft.it.soft.service.util.FileProcessor.clearFilesByDirectory;
import static ua.code.intership.proft.it.soft.service.util.constant.DefaultConst.DEFAULT_STATISTICS_INFO_SET;
import static ua.code.intership.proft.it.soft.service.util.constant.FileTestConst.PATH_TO_TEST_JSON_FILE_LIST;
import static ua.code.intership.proft.it.soft.service.util.constant.FileTestConst.PATH_TO_TEST_XML_FILE_LIST;
import static ua.code.intership.proft.it.soft.service.util.constant.ParserConst.DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES;
import static ua.code.intership.proft.it.soft.ui.util.constant.PlanetGeneratorConst.PLANET_LISTS;

@ExtendWith(MockitoExtension.class)
class XmlFileCreatorTest {
    private FileCreator fileCreator;
    @Mock
    private StatisticsProcessor statisticsProcessor;

    @BeforeEach
    void setUp() {
        fileCreator = new XmlFileCreator();
        ObjectMultipleFileGenerator<Planet> objectMultipleFileGenerator = new JsonObjectMultipleFileGenerator<>();

        objectMultipleFileGenerator.generate(PATH_TO_TEST_JSON_FILE_LIST, PLANET_LISTS, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES);
        File[] files = new File(PATH_TO_TEST_JSON_FILE_LIST).listFiles();

        assertNotNull(files, "files should not be null!");
        assertNotNull(PLANET_LISTS, "PLANET_LISTS should not be null!");

        assertEquals(files.length, PLANET_LISTS.size());
    }

    @AfterEach
    void tearDown() {
        clearFilesByDirectory(PATH_TO_TEST_JSON_FILE_LIST);
        clearFilesByDirectory(PATH_TO_TEST_XML_FILE_LIST);
    }

    @Test
    void testGenerateXmlStatisticsFileShouldWithoutThrow() {
        assertDoesNotThrow(() -> {
            Mockito.when(statisticsProcessor.getStatisticsSortedSet(Mockito.any()))
                   .thenReturn(DEFAULT_STATISTICS_INFO_SET);
            ClassMetadataProvider.reflectInitMockStateForService(fileCreator, "statisticsProcessor", statisticsProcessor);
            fileCreator.generate(PATH_TO_TEST_XML_FILE_LIST, Planet.Fields.atmosphericComposition.name());
        });
    }

    @Test
    void testGenerateXmlStatisticsFileShouldThrowNullPath() {
        assertDoesNotThrow(() -> {
            Mockito.when(statisticsProcessor.getStatisticsSortedSet(Mockito.any()))
                   .thenReturn(DEFAULT_STATISTICS_INFO_SET);
            ClassMetadataProvider.reflectInitMockStateForService(fileCreator, "statisticsProcessor", statisticsProcessor);
        });

        Throwable exception = assertThrows(FileGenerationException.class, () -> {
            fileCreator.generate(null, Planet.Fields.atmosphericComposition.name());
        });
        assertEquals("Error generating XML file", exception.getMessage());

    }
}