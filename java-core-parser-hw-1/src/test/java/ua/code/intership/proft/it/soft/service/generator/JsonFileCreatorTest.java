package ua.code.intership.proft.it.soft.service.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.service.util.TimeChecker;

import static ua.code.intership.proft.it.soft.service.util.constant.FileConstant.PATH_TO_JSON_FILE_LIST;
import static ua.code.intership.proft.it.soft.service.util.constant.FileConstant.PLANET_LISTS;

class JsonFileCreatorTest {

    private MultipleFileGenerator<Planet> multipleFileGenerator;

    @BeforeEach
    void setUp() {
        multipleFileGenerator = new JsonMultipleFileCreator<>();
    }

    @Test
    void testGenerateJSONFiles() {
        TimeChecker.timeTest(() -> Assertions.assertDoesNotThrow(() ->
                multipleFileGenerator.generate(PATH_TO_JSON_FILE_LIST, PLANET_LISTS, 10)));
    }

}