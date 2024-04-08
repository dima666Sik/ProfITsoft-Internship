package ua.code.intership.proft.it.soft.service.statistic;

import ua.code.intership.proft.it.soft.model.dto.StatisticsDto;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Set;

public interface StatisticsProcessor {
    void collectStatistics(File file, String attribute) throws IOException;
    Set<StatisticsDto> getStatisticsSet() throws IllegalAccessException;
    Set<StatisticsDto> getStatisticsSortedSet(Comparator<StatisticsDto> comparator) throws IllegalAccessException;
}
