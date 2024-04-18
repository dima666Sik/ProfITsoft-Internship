package ua.code.intership.proft.it.soft.service.reader;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * The FileReader interface provides a contract for classes that are responsible for reading data from files.
 */
public interface FileReader {

    /**
     * Reads data from the specified file and processes it using the provided consumer.
     *
     * @param file     the file to read
     * @param clazzObj the class object representing the structure of the data in file
     * @param consumer the consumer function to process the data read from the file
     * @param <T>      the type of object to be read from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    <T> void readFile(File file, Class<T> clazzObj, Consumer<T> consumer) throws IOException;
}
