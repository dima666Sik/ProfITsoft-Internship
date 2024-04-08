package ua.code.intership.proft.it.soft.service.util.constant;

import ua.code.intership.proft.it.soft.model.Planet;

import java.io.File;
import java.util.List;

import static ua.code.intership.proft.it.soft.service.util.constant.ConstantGenerator.getPlanets;
import static ua.code.intership.proft.it.soft.service.util.random.Randomize.generateRandomize;

public final class FileConstant {
    public static final String PATH_TO_JSON_FILE_LIST = "src/test/resources/json-files";
    public static final String PATH_TO_XML_FILE_LIST = "src/test/resources/xml-files";

    public static final List<File> FILE_JSON_LIST = List.of(
            new File(PATH_TO_JSON_FILE_LIST + File.separator + "planet1.json"),
            new File(PATH_TO_JSON_FILE_LIST + File.separator + "planet2.json"),
            new File(PATH_TO_JSON_FILE_LIST + File.separator + "planet3.json")
    );

    public static final List<List<Planet>> PLANET_LISTS = List.of(
            getPlanets(generateRandomize(5, 10)),
            getPlanets(generateRandomize(7, 12)),
            getPlanets(generateRandomize(3, 8))
    );

    private FileConstant() {
    }

}
