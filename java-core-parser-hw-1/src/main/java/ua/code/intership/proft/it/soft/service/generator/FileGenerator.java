package ua.code.intership.proft.it.soft.service.generator;

import java.io.File;

public interface FileGenerator<T> {
    File generate(String pathToXmlDirectory, T[] files, int countThreads, String attribute);
}
