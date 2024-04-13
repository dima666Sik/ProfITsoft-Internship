package ua.code.intership.proft.it.soft.service.util.parser;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A utility class for parsing strings.
 */
public final class StringParser {
    private StringParser() {
    }

    /**
     * Parses a separated list using these symbols [<b> , # | ; </b>] into a {@code Set} of strings.
     *
     * @param inputStr the input string
     * @param regEx the regular expression to split the input string into a set of strings
     * @return a {@code Set} of strings
     */
    public static Set<String> parseToStringSet(String inputStr, String regEx) {
        return Arrays.stream(inputStr.split(regEx))
               .map(String::trim)
               .collect(Collectors.toSet());
    }
}
