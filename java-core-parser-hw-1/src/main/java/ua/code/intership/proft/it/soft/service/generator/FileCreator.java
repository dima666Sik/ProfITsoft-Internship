package ua.code.intership.proft.it.soft.service.generator;

import java.io.File;
import java.util.List;

/**
 * A functional interface that defines the contract for creating files.
 *
 * @param <T> the type of data used to create the file
 */
@FunctionalInterface
public interface FileCreator<T> {

    /**
     * Creates a file based on the specified parameters.
     *
     * @param pathToDirectory the path to the directory that will be contained the result-generated file
     * @param data            the data used to create the file
     * @param countThreads    the number of threads used to processing data
     * @param attribute       the attribute used to create the file name
     * @return the created file
     */
    File generate(String pathToDirectory, List<T> data, int countThreads, String attribute);

}
