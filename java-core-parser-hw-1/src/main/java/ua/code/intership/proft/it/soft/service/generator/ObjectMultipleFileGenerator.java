package ua.code.intership.proft.it.soft.service.generator;

import java.util.List;

/**
 * A functional interface for generating multiple files based on objects.
 *
 * @param <T> the type of object to generate
 */
@FunctionalInterface
public interface ObjectMultipleFileGenerator<T> {

    /**
     * Generates multiple files with a list of objects.
     *
     * @param pathToDirectory the directory path to store the generated files
     * @param listObjectsList the list of objects to generate
     * @param countThreads    the number of threads to use for generation
     */
    void generate(String pathToDirectory, List<List<T>> listObjectsList, int countThreads);

}
