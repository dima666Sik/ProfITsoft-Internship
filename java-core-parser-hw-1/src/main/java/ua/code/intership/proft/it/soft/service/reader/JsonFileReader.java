package ua.code.intership.proft.it.soft.service.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import lombok.extern.log4j.Log4j2;
import ua.code.intership.proft.it.soft.model.Planet;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import static ua.code.intership.proft.it.soft.service.util.constant.JacksonProvider.OBJECT_MAPPER;

/**
 * A JSON file reader that uses Jackson to parse and deserialize objects from a JSON file.
 */
@Log4j2
public class JsonFileReader implements FileReader {
    private final JsonFactory jsonFactory = new JsonFactory();

    /**
     * Reads a JSON file and deserializes the objects into the specified class.
     * Reading the JSON file will be by small parts for height performance
     * because we avoid reading the entire JSON file, so we won't see {@link OutOfMemoryError}
     * if the file is large
     *
     * @param file           the JSON file
     * @param clazzObjInJson the class object of the object in the JSON file
     * @param consumer       a consumer that accepts the deserialized object to the future process
     * @throws IOException if there is an error reading the file or parsing the JSON
     */
    @Override
    public <T> void readFile(File file, Class<T> clazzObjInJson, Consumer<T> consumer) throws IOException {
        try (JsonParser jsonParser = jsonFactory.createParser(file)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                log.error("JSON data should start with an array but now it is: " + jsonParser.currentToken());
                throw new IllegalArgumentException("JSON data should start with an array");
            }
            try {
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

                    T obj = OBJECT_MAPPER.readValue(jsonParser, clazzObjInJson);
                    if (!isValid(obj)) {
                        log.warn("Invalid data found: " + obj.toString());
                    } else {
                        consumer.accept(obj);
                    }
                }
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON data: " + e.getMessage());
            }
        }
    }

    private <T> boolean isValid(T obj) {
        if (obj == null) {
            log.warn("Invalid data: null object");
            return false;
        }

        if (obj instanceof Planet planet) {
            if (planet.getId() <= 0) {
                log.warn("Invalid data: Invalid ID");
                return false;
            }
            if (planet.getName() == null || planet.getName().isEmpty()) {
                log.warn("Invalid data: Name is required");
                return false;
            }
            if (planet.getMass() == null || planet.getMass().getValue() <= 0) {
                log.warn("Invalid data: Invalid mass");
                return false;
            }
            if (planet.getDiameter() == null || planet.getDiameter().getValue() <= 0) {
                log.warn("Invalid data: Invalid diameter");
                return false;
            }
        } else {
            log.warn("Unsupported data type: " + obj.getClass().getSimpleName());
            return false;
        }

        return true;
    }
}
