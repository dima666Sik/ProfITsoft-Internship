package ua.code.intership.proft.it.soft.service.util.constant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * A utility class that provides the instance of {@link ObjectMapper}
 * for use throughout the application and can have more features to
 * interact with Jackson library.
 */
public final class JacksonProvider {

    private JacksonProvider() {
    }

    public static final ObjectMapper OBJECT_MAPPER;

    static {
        ObjectMapper mapper = new ObjectMapper();
        // Don't throw exception if json has extra fields without serialization.
        // This is useful when you want to use pojo to deserialize and only cares
        // about the json part
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Ignore null values when writing json.
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Write the time as a string instead of long so that it is human-readable.
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        OBJECT_MAPPER = mapper;
    }


}
