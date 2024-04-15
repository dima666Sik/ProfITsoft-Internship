package ua.code.intership.proft.it.soft.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.service.generator.ObjectMultipleFileGenerator;
import ua.code.intership.proft.it.soft.service.parser.FileParser;
import ua.code.intership.proft.it.soft.service.util.reflect.ClassMetadataProvider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class ConsoleMenuUITest {
    @Mock
    private ObjectMultipleFileGenerator<Planet> multipleFileGenerator;
    @Mock
    private FileParser fileParser;

    @Test
    void testMenuFirstCase() {
        ConsoleMenuUI consoleMenuUI = new ConsoleMenuUI();

        Mockito.doNothing()
               .when(multipleFileGenerator)
               .generate(anyString(), any(), anyInt());

        Scanner scanner = mockScanner("1\n3\n");

        assertDoesNotThrow(() -> {
            ClassMetadataProvider.reflectInitMockStateForService(consoleMenuUI, "multipleFileGenerator", multipleFileGenerator);
            ClassMetadataProvider.reflectInitMockStateForService(consoleMenuUI, "scanner", scanner);
        });

        consoleMenuUI.start();
    }

    @Test
    void testMenuSecondCase() {
        ConsoleMenuUI consoleMenuUI = new ConsoleMenuUI();

        Mockito.when(fileParser.parse(anyString(), anyString()))
               .thenReturn(new File("test.xml"));

        Scanner scanner = mockScanner("2\n-\n-\n3\n");

        assertDoesNotThrow(() -> {
            ClassMetadataProvider.reflectInitMockStateForService(consoleMenuUI, "fileParser", fileParser);
            ClassMetadataProvider.reflectInitMockStateForService(consoleMenuUI, "scanner", scanner);
        });

        assertDoesNotThrow(consoleMenuUI::start);
    }

    @Test
    void testMenuThirdCase() {
        ConsoleMenuUI consoleMenuUI = new ConsoleMenuUI();

        Scanner scanner = mockScanner("3\n");

        assertDoesNotThrow(() ->
                ClassMetadataProvider.reflectInitMockStateForService(consoleMenuUI, "scanner", scanner));

        assertDoesNotThrow(consoleMenuUI::start);
    }

    @Test
    void testMenuDefaultCase() {
        ConsoleMenuUI consoleMenuUI = new ConsoleMenuUI();

        Scanner scanner = mockScanner("10\n3\n");

        assertDoesNotThrow(() ->
                ClassMetadataProvider.reflectInitMockStateForService(consoleMenuUI, "scanner", scanner));

        assertDoesNotThrow(consoleMenuUI::start);
    }

    private Scanner mockScanner(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        return new Scanner(System.in);
    }
}