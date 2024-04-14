package ua.code.intership.proft.it.soft.service.generator;

import java.io.File;

/**
 * A functional interface that defines the contract for creating files.
 */
@FunctionalInterface
public interface FileCreator {

    /**
     * Creates a file based on the specified parameters.
     *
     * @param pathToDirectory the path to the directory that will be contained the result-generated file
     * @param attribute       the attribute used to create the file name
     * @return the created file
     */
    File generate(String pathToDirectory, String attribute);

}
