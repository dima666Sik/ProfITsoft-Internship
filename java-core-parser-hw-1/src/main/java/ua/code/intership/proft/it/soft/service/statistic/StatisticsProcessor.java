package ua.code.intership.proft.it.soft.service.statistic;

import ua.code.intership.proft.it.soft.model.StatisticsInfo;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Set;

/**
 * Interface for processing statistical data.
 */
public interface StatisticsProcessor {

    /**
     * Collects statistical data from the specified file.
     *
     * @param file      the file to collect data from
     * @param attribute the name of the attribute to collect statistics for
     * @throws IOException if an I/O error occurs
     */
    void collectStatistics(File file, String attribute) throws IOException;

    /**
     * Returns a set of statistical information.
     *
     * @return a set of statistical information
     * @throws IllegalAccessException if the set cannot be accessed
     */
    Set<StatisticsInfo<? extends Comparable<?>>> getStatisticsSet() throws IllegalAccessException;

    /**
     * Returns a sorted set of statistical information.
     *
     * @param comparator the comparator to use for sorting
     * @return a sorted set of statistical information
     * @throws IllegalAccessException if the set cannot be accessed
     */
    Set<StatisticsInfo<? extends Comparable<?>>> getStatisticsSortedSet(Comparator<StatisticsInfo<? extends Comparable<?>>> comparator) throws IllegalAccessException;
    /**
     * Clears the set of collected statistics.
     */
    void clearStatisticsSet();
}
