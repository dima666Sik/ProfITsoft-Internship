package ua.code.intership.proft.it.soft.ui.util.constant;

import static ua.code.intership.proft.it.soft.model.attribute.PlanetAttribute.*;
import static ua.code.intership.proft.it.soft.service.util.constant.FileConst.PATH_TO_JSON_FILE_LIST;
import static ua.code.intership.proft.it.soft.service.util.constant.FileConst.PATH_TO_XML_FILE_LIST;

public final class ConsoleConst {
    private ConsoleConst() {
    }

    public static final String MESSAGE_TO_GENERATE_JSON_FILE = "Generating JSON files with random info about planets.";
    public static final String MESSAGE_TO_GENERATE_STATISTICS_FILE = "Getting statistics from files to XML file.";
    public static final String MESSAGE_TO_EXIT = "Exit.";
    public static final String MAIN_MENU_APP = """
            Menu:
                1. %s
                2. %s
                3. %s
            """.formatted(MESSAGE_TO_GENERATE_JSON_FILE, MESSAGE_TO_GENERATE_STATISTICS_FILE, MESSAGE_TO_EXIT);
    public static final String MESSAGE_TO_CHOOSE_FUNCTION_FROM_THE_MENU = "Please enter the number of the function from the menu.";
    public static final String YOUR_CHOICE = "Your choice is - ";

    public static final String CHOSE_MESSAGE = "You chose: ";
    public static final String MESSAGE_TO_NOTIFY_ABOUT_INVALID_INPUT = "You enter invalid number!";
    public static final int DEFAULT_COUNT_THREADS_TO_PROCESSING_JSON_FILES = 3;

    public static final String RECOMMENDED_TO_CHOSE_DATA = """
                ==============================================
                Recommendations (you can copy and paste them):
                    2.1 The path to Json files: %s
                    2.2 The path to directory to Xml store file (default): %s
                    2.3 Attributes that you can choose to generate statistics (2.3.5 recommended):
                        2.3.1 %s
                        2.3.2 %s
                        2.3.3 %s
                        2.3.4 %s
                        2.3.5 %s
                ==============================================
            """.formatted(PATH_TO_JSON_FILE_LIST,
            PATH_TO_XML_FILE_LIST,
            ID,
            NAME,
            HAS_RINGS,
            HAS_MOONS,
            ATMOSPHERIC_COMPOSITION);
}
