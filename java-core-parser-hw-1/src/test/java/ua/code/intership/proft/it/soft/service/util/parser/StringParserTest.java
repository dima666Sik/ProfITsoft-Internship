package ua.code.intership.proft.it.soft.service.util.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringParserTest {

    @ParameterizedTest
    @ValueSource(strings = {"planet1, planet2, planet3"})
    void testParseToStringSetWithGivenComma(String input) {
        Set<String> result = StringParser.parseToStringSet(input, ",");

        assertEquals(3, result.size());
        assertTrue(result.contains("planet1"));
        assertTrue(result.contains("planet2"));
        assertTrue(result.contains("planet3"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"planet1#planet2#planet3#planet4"})
    void testParseToStringSetWithGivenHash(String input) {
        Set<String> result = StringParser.parseToStringSet(input, "#");

        assertEquals(4, result.size());
        assertTrue(result.contains("planet1"));
        assertTrue(result.contains("planet2"));
        assertTrue(result.contains("planet3"));
        assertTrue(result.contains("planet4"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"planet1|planet2| planet3"})
    void testParseToStringSetWithGivenPipeline(String input) {
        Set<String> result = StringParser.parseToStringSet(input, "\\|");

        assertEquals(3, result.size());
        assertTrue(result.contains("planet1"));
        assertTrue(result.contains("planet2"));
        assertTrue(result.contains("planet3"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"planet1; planet2;"})
    void testParseToStringSetWithGivenSemiColon(String input) {
        Set<String> result = StringParser.parseToStringSet(input, ";");

        assertEquals(2, result.size());
        assertTrue(result.contains("planet1"));
        assertTrue(result.contains("planet2"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"planet1#planet2; planet3, planet3| planet4; planet5;"})
    void testParseToStringSetWithGivenCombineSeparateSymbols(String input) {
        Set<String> result = StringParser.parseToStringSet(input, "[,#|;]");

        assertEquals(5, result.size());
        assertTrue(result.contains("planet1"));
        assertTrue(result.contains("planet2"));
        assertTrue(result.contains("planet3"));
        assertTrue(result.contains("planet4"));
        assertTrue(result.contains("planet5"));
    }

}