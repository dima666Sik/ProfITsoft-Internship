package ua.code.intership.proft.it.soft.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A utility class that provides a single instance of {@link ObjectMapper}
 * for use throughout the application and can have more features to
 * interact with Jackson library.
 */
public final class JacksonConst {

    private JacksonConst() {
    }

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

}
