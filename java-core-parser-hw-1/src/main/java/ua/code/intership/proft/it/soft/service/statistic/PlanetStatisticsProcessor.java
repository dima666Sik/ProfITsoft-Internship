package ua.code.intership.proft.it.soft.service.statistic;

import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.service.reader.FileReader;
import ua.code.intership.proft.it.soft.service.reader.JsonFileReader;

import java.io.File;
import java.io.IOException;

/**
 * {@link PlanetStatisticsProcessor} is a class that extends {@link AbstractStatisticsProcessor} and
 * implements the {@link StatisticsProcessor} interface that
 * provides functionality to collect statistics from JSON data related to planets.
 * It processes JSON files, extracts specified attributes, and collects statistics on them.
 * <p> This class is Singleton that gather statistics information.
 *
 * @see StatisticsProcessor
 */
public final class PlanetStatisticsProcessor extends AbstractStatisticsProcessor implements StatisticsProcessor {
    /**
     * The singleton instance of the StatisticsProcessor.
     * This instance ensures that there is only one object of StatisticsProcessor throughout the application.
     */
    private static StatisticsProcessor instance;

    /**
     * The {@link FileReader} object used to read JSON files.
     * It is initialized with an instance of {@link JsonFileReader}.
     */
    private final FileReader fileReader = new JsonFileReader();

    /**
     * A private constructor that creates a new instance of the {@link PlanetStatisticsProcessor} class.
     * This constructor is marked as private so that it cannot be instantiated from outside the class.
     * To get instance we can use only {@link #getInstance()}
     */
    private PlanetStatisticsProcessor() {
    }

    /**
     * Returns the instance of {@link PlanetStatisticsProcessor}.
     * If the instance is already created, it is returned.
     * If the instance is not created, a new instance is created and returned.
     * In this case, we guarantee the allowing to a shared resource
     * because class has only one instance.
     *
     * @return the instance of {@link PlanetStatisticsProcessor}
     */
    public static StatisticsProcessor getInstance() {
        if (instance != null) return instance;
        return new PlanetStatisticsProcessor();
    }

    /**
     * Processes the given JSON file into {@link  FileReader#readFile} method and
     * collects statistics for the given attribute using consumer parameter.
     * The collected statistics are stored in a set, which can be retrieved using the {@link #getStatisticsSet} method.
     *
     * @param file      the JSON file to process
     * @param attribute the attribute to collect statistics for
     * @throws IOException if an I/O error occurs while reading the file
     */
    @Override
    public void collectStatistics(File file, String attribute) throws IOException {
        fileReader.readFile(file, Planet.class, planet -> {
            switch (attribute) {
                case "id" -> processStatistic(planet.getId());
                case "name" -> processStatistic(planet.getName());
                case "hasMoons" -> processStatistic(planet.isHasMoons());
                case "hasRings" -> processStatistic(planet.isHasRings());
                case "atmosphericComposition" -> processStatistic(planet.getAtmosphericComposition());
                default -> throw new IllegalArgumentException("Unknown attribute: " + attribute);
            }
        });
    }
}
