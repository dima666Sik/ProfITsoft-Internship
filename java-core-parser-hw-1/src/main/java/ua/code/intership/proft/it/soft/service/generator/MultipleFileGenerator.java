package ua.code.intership.proft.it.soft.service.generator;

import java.util.List;

public interface MultipleFileGenerator<T>{
    void generate(String pathToJsonDirectory, List<List<T>> listObjectsList, int countThreads);
}
