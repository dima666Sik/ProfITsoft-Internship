package ua.code.intership.proft.it.soft.service.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.service.generator.JsonObjectMultipleFileGenerator;
import ua.code.intership.proft.it.soft.service.generator.ObjectMultipleFileGenerator;
import ua.code.intership.proft.it.soft.service.util.reflect.ClassMetadataProvider;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.code.intership.proft.it.soft.service.util.FileProcessor.clearFilesByDirectory;
import static ua.code.intership.proft.it.soft.ui.util.constant.PlanetGeneratorConst.PLANET_LISTS;
import static ua.code.intership.proft.it.soft.service.util.constant.ParserConst.DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES;
import static ua.code.intership.proft.it.soft.service.util.constant.FileTestConst.*;

@ExtendWith(MockitoExtension.class)
class JsonFileReaderTest {
    private FileReader fileReader;
    private File file;
    @Mock
    private JsonFactory jsonFactory;

    @BeforeEach
    void setUp() {
        fileReader = new JsonFileReader();
        ObjectMultipleFileGenerator<Planet> objectMultipleFileGenerator = new JsonObjectMultipleFileGenerator<>();
        List<List<Planet>> listOfObjects = PLANET_LISTS.subList(0, 1);
        objectMultipleFileGenerator.generate(PATH_TO_TEST_JSON_FILE_LIST, listOfObjects, DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES);
        File[] files = new File(PATH_TO_TEST_JSON_FILE_LIST).listFiles();
        assertNotNull(files, "files should not be null!");
        file = files[0];
    }

    @AfterEach
    void tearDown() {
        clearFilesByDirectory(PATH_TO_TEST_JSON_FILE_LIST);
    }

    @Test
    void readFileShouldNotThrow() {
        assertDoesNotThrow(() ->
                fileReader.readFile(file, Planet.class, planet ->
                        assertNotNull(planet, "Planet should not be null!")));
    }

    @Test
    void readFileShouldThrow() {
        assertDoesNotThrow(() -> {
            JsonParser jsonParser = Mockito.mock(JsonParser.class);
            Mockito.when(jsonFactory.createParser(file)).thenReturn(jsonParser);
            Mockito.when(jsonParser.nextToken()).thenReturn(JsonToken.VALUE_NULL);
            ClassMetadataProvider.reflectInitMockStateForService(fileReader, "jsonFactory", jsonFactory);
        });
        Throwable throwable = assertThrows(IllegalArgumentException.class, () ->
                fileReader.readFile(file, Planet.class, planet ->
                        assertNotNull(planet, "Planet should not be null!")));
        assertEquals("JSON data should start with an array", throwable.getMessage());
    }

}