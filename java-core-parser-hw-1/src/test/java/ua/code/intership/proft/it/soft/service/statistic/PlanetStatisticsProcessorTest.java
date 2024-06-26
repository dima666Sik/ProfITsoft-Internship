package ua.code.intership.proft.it.soft.service.statistic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.model.StatisticsInfo;
import ua.code.intership.proft.it.soft.service.generator.JsonObjectMultipleFileGenerator;
import ua.code.intership.proft.it.soft.service.generator.ObjectMultipleFileGenerator;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ua.code.intership.proft.it.soft.service.util.FileProcessor.clearFilesByDirectory;
import static ua.code.intership.proft.it.soft.ui.util.constant.PlanetGeneratorConst.PLANET_LISTS;
import static ua.code.intership.proft.it.soft.service.util.constant.ParserConst.DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES;
import static ua.code.intership.proft.it.soft.service.util.constant.FileTestConst.*;

class PlanetStatisticsProcessorTest {
    private StatisticsProcessor processor;
    private File validJsonFile;

    @BeforeEach
    void setUp() {
        processor = PlanetStatisticsProcessor.getInstance();
        ObjectMultipleFileGenerator<Planet> objectMultipleFileGenerator = new JsonObjectMultipleFileGenerator<>();
        objectMultipleFileGenerator.generate(PATH_TO_TEST_JSON_FILE_LIST, PLANET_LISTS.subList(0, 1), DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES);
        File[] files = new File(PATH_TO_TEST_JSON_FILE_LIST).listFiles();

        assertNotNull(files, "files should not be null!");
        assertNotNull(PLANET_LISTS, "PLANET_LISTS should not be null!");
        validJsonFile = files[0];
    }

    @AfterEach
    void tearDown() {
        clearFilesByDirectory(PATH_TO_TEST_JSON_FILE_LIST);
    }

    @Test
    void testCollectStatisticsWithValidJsonFileAndAttribute() {
        assertDoesNotThrow(() -> processor.collectStatistics(validJsonFile, Planet.Fields.id.name()));
        assertDoesNotThrow(() -> processor.collectStatistics(validJsonFile, Planet.Fields.hasMoons.name()));
        assertDoesNotThrow(() -> processor.collectStatistics(validJsonFile, Planet.Fields.name.name()));
        assertDoesNotThrow(() -> processor.collectStatistics(validJsonFile, Planet.Fields.hasRings.name()));
        assertDoesNotThrow(() -> processor.collectStatistics(validJsonFile, Planet.Fields.atmosphericComposition.name()));
    }

    @Test
    void testCollectStatisticsUnknownAttribute() {
        String unknownAttribute = "unknownAttribute";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> processor.collectStatistics(validJsonFile, unknownAttribute));
        assertEquals("Unknown attribute: " + unknownAttribute, exception.getMessage());
    }


    @Test
    void testGetStatisticsSortedSetShouldWithoutThrow() {
        assertDoesNotThrow(() -> processor.collectStatistics(validJsonFile, Planet.Fields.atmosphericComposition.name()));
        assertDoesNotThrow(() -> processor.getStatisticsSortedSet((el1, el2) -> el2.getNumberOfRepetitions() - el1.getNumberOfRepetitions()));
    }

    @Test
    void testGetStatisticsSortedSetShouldReturnSortedSet() {
        assertDoesNotThrow(() -> processor.collectStatistics(validJsonFile, Planet.Fields.atmosphericComposition.name()));

        assertDoesNotThrow(() -> {
            Comparator<StatisticsInfo<? extends Comparable<?>>> comparatorByNumberOfRepetitions = (el1, el2) -> el2.getNumberOfRepetitions() - el1.getNumberOfRepetitions();

            Set<StatisticsInfo<? extends Comparable<?>>> standardStatisticsInfoSet
                    = processor.getStatisticsSet();
            Set<StatisticsInfo<? extends Comparable<?>>> sortedByNumOfRepetitionStatisticsInfoSet
                    = processor.getStatisticsSortedSet(comparatorByNumberOfRepetitions);

            Set<StatisticsInfo<? extends Comparable<?>>> resultSortByNumberOfRepetitions = standardStatisticsInfoSet.stream()
                                                                                                                    .sorted(comparatorByNumberOfRepetitions)
                                                                                                                    .collect(Collectors.toCollection(LinkedHashSet::new));
            assertEquals(resultSortByNumberOfRepetitions, sortedByNumOfRepetitionStatisticsInfoSet);
        });


    }

}