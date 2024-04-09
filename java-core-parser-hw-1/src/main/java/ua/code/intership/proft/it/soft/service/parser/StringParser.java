package ua.code.intership.proft.it.soft.service.parser;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StringParser implements TypeParser<String> {
    @Override
    public Set<String> parseToSet(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }
}
