package ua.code.intership.proft.it.soft.service.statistic;

import ua.code.intership.proft.it.soft.model.StatisticsInfo;
import ua.code.intership.proft.it.soft.service.util.parser.StringParser;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
/**
 * AbstractStatisticsProcessor is an abstract class that provides common functionality for processing statistics.
 * It implements the StatisticsProcessor interface and provides methods to collect and manage statistics information.
 * Subclasses can extend this class to implement specific logic for processing statistics related to different data types.
 *
 * <p>The class maintains a set to store statistics information of type {@link StatisticsInfo}.
 * Each StatisticsInfo object contains statistical data related to the processed information with an unknown type
 * that extends the {@link Comparable} interface, allowing the statistics to be sorted by an unknown attribute.</p>
 *
 * @see StatisticsProcessor
 */
public abstract class AbstractStatisticsProcessor implements StatisticsProcessor {
    /**
     * The set to store statistics information.
     * It holds objects of type {@link StatisticsInfo}, which contain statistical data
     * related to the processed information with unknown type that extends {@link Comparable} interface
     * that allows to sorted by unknown attribute.
     */
    private static final Set<StatisticsInfo<? extends Comparable<?>>> STATISTICS_INFO_DTO_SET;

    static {
        STATISTICS_INFO_DTO_SET = ConcurrentHashMap.newKeySet();
    }

    /**
     * Processes the given attribute value.
     * If the attribute value is a string, it extracts the set of strings from it and adds them to the set of collected statistics.
     * Otherwise, it adds the attribute value to the set of collected statistics.
     *
     * @param valueAttribute the attribute value to process
     */
    protected  <T extends Comparable<T>> void processStatistic(T valueAttribute) {
        if (isStringAttribute(valueAttribute)) {
            Set<String> setStringAttributes = getSetStringAttributes((String) valueAttribute);
            setStringAttributes.forEach(this::addStatistic);
            return;
        }
        addStatistic(valueAttribute);
    }

    /**
     * Checks if the given attribute is present in the set of collected statistics.
     *
     * @param attribute the attribute to check
     * @return true if the given attribute is present in the set of collected statistics, false otherwise
     */
    private <T extends Comparable<T>> boolean isExistInSetStatistics(T attribute) {
        return STATISTICS_INFO_DTO_SET.stream()
                                      .anyMatch(el -> el.getAttribute()
                                                        .equals(attribute));
    }

    /**
     * Extracts the set of strings from the given string, which is separated by [<b> , # | ; </>].
     * If the given string does not contain any of these separators, the original string is returned as a set.
     *
     * @param strAttribute the string to extract the set of strings from
     * @return the set of strings extracted from the given string
     */
    private Set<String> getSetStringAttributes(String strAttribute) {
        String regEx = "[,#|;]";
        if (strAttribute.matches(".*" + regEx + ".*")) {
            return StringParser.parseToStringSet(strAttribute, regEx);
        } else return Set.of(strAttribute);
    }

    private <T extends Comparable<T>> boolean isStringAttribute(T valueAttribute) {
        return valueAttribute instanceof String;
    }

    /**
     * Creates a new {@link StatisticsInfo} object with the given statistic and number of repetitions,
     * and adds it to the set of collected statistics. If the statistic is already present,
     * the number of repetitions is incremented.
     *
     * @param valueAttribute is attribute value that will be added to statistic info
     */
    private synchronized <T extends Comparable<T>> void addStatistic(T valueAttribute) {

        if (isExistInSetStatistics(valueAttribute)) {
            Optional<StatisticsInfo<?>> optionalStatisticsDto = STATISTICS_INFO_DTO_SET.stream()
                                                                                       .filter(el -> el.getAttribute()
                                                                                                       .equals(valueAttribute))
                                                                                       .findFirst();
            optionalStatisticsDto.ifPresent(StatisticsInfo::incrementNumberOfRepetitions);
            return;
        }

        StatisticsInfo<? extends Comparable<T>> statisticsInfo = StatisticsInfo.<T>builder()
                                                                               .attribute(valueAttribute)
                                                                               .numberOfRepetitions(1)
                                                                               .build();
        STATISTICS_INFO_DTO_SET.add(statisticsInfo);
    }

    /**
     * Returns a default set of all collected statistics.
     *
     * @return a set of all collected statistics in default set
     * @throws IllegalAccessException if the set of statistics is empty
     */
    @Override
    public Set<StatisticsInfo<? extends Comparable<?>>> getStatisticsSet() throws IllegalAccessException {
        if (STATISTICS_INFO_DTO_SET.isEmpty()) throw new IllegalAccessException("Set hasn't statistics!");
        return STATISTICS_INFO_DTO_SET;
    }

    /**
     * Returns a sorted set of all collected statistics.
     *
     * @param comparator a comparator to sort the statistics
     * @return a sorted set of all collected statistics
     * @throws IllegalAccessException if the set of statistics is empty
     */
    @Override
    public Set<StatisticsInfo<? extends Comparable<?>>> getStatisticsSortedSet(Comparator<StatisticsInfo<? extends Comparable<?>>> comparator) throws IllegalAccessException {
        return getStatisticsSet().stream()
                                 .sorted(comparator)
                                 .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
