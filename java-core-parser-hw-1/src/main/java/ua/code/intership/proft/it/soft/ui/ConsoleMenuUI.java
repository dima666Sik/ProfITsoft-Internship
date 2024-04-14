package ua.code.intership.proft.it.soft.ui;

import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.service.generator.JsonObjectMultipleFileGenerator;
import ua.code.intership.proft.it.soft.service.generator.ObjectMultipleFileGenerator;
import ua.code.intership.proft.it.soft.service.parser.FileParser;
import ua.code.intership.proft.it.soft.service.parser.JsonToXmlParser;

import java.util.Scanner;

import static ua.code.intership.proft.it.soft.ui.util.constant.PlanetGeneratorConst.PLANET_LISTS;
import static ua.code.intership.proft.it.soft.ui.util.constant.ConsoleConst.*;
import static ua.code.intership.proft.it.soft.ui.util.ConsolePrinter.*;
import static ua.code.intership.proft.it.soft.ui.util.constant.FileConst.*;

public class ConsoleMenuUI {
    private final ObjectMultipleFileGenerator<Planet> multipleFileGenerator = new JsonObjectMultipleFileGenerator<>();
    private final FileParser fileParser = new JsonToXmlParser();

    private boolean menu() {
        printMessageWithNewLine(MAIN_MENU_APP);
        printMessageWithNewLine(MESSAGE_TO_CHOOSE_FUNCTION_FROM_THE_MENU);
        printMessageWithoutNewLine(YOUR_CHOICE);
        Scanner scanChoice = new Scanner(System.in);
        int choice = scanChoice.nextInt();
        switch (choice) {
            case 1 -> {
                printMessageWithNewLine(CHOSE_MESSAGE + MESSAGE_TO_GENERATE_JSON_FILE);
                multipleFileGenerator.generate(PATH_TO_JSON_FILE_LIST, PLANET_LISTS, DEFAULT_COUNT_THREADS_TO_PROCESSING_JSON_FILES);
                return true;
            }
            case 2 -> {
                printMessageWithNewLine(CHOSE_MESSAGE + MESSAGE_TO_GENERATE_STATISTICS_FILE);
                printMessageWithNewLine(RECOMMENDED_TO_CHOSE_DATA);

                Scanner scanner = new Scanner(System.in);

                printMessageWithoutNewLine("Write path to json files: ");
                String pathToJsonFiles = scanner.nextLine();

                printMessageWithoutNewLine("Write path to will be generated xml file: ");
                String pathToXmlFile = scanner.nextLine();

                printMessageWithoutNewLine("Choose attribute to generate statistics: ");
                String attribute = scanner.nextLine();

                fileParser.parse(pathToJsonFiles, pathToXmlFile, attribute);
                return true;
            }
            case 3 -> {
                printMessageWithNewLine(MESSAGE_TO_EXIT);
                scanChoice.close();
                return false;
            }
            default -> {
                printMessageWithNewLine(MESSAGE_TO_NOTIFY_ABOUT_INVALID_INPUT);
                return true;
            }
        }

    }

    public void start() {
        while (true) if (!menu()) break;
    }
}
