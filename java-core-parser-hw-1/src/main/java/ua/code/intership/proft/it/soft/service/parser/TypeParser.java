package ua.code.intership.proft.it.soft.service.parser;

import java.util.Set;

public interface TypeParser<T extends Comparable<T>> {
    Set<T> parseToSet(T input);
}
