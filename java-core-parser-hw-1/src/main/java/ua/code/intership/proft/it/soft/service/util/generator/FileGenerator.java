package ua.code.intership.proft.it.soft.service.util.generator;

import lombok.extern.log4j.Log4j2;
import ua.code.intership.proft.it.soft.service.exception.FileGenerationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
public final class FileGenerator {
    private FileGenerator() {
    }

    public static void generateDirectory(Path pathToDirectory) {
        try {
            Files.createDirectories(pathToDirectory);
            log.info("Directory created successfully: {}", pathToDirectory);
        } catch (IOException e) {
            log.error("Error auto creating directory: {}", e.getMessage());
            throw new FileGenerationException("Error generating directory: " + pathToDirectory);
        }
    }
}
