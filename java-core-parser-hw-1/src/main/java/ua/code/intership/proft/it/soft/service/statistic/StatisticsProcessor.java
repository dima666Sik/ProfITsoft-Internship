package ua.code.intership.proft.it.soft.service.statistic;

import ua.code.intership.proft.it.soft.model.dto.StatisticsInfoDto;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Set;

public interface StatisticsProcessor {
    void collectStatistics(File file, String attribute) throws IOException;
    Set<StatisticsInfoDto<? extends Comparable<?>>> getStatisticsSet() throws IllegalAccessException;
    Set<StatisticsInfoDto<? extends Comparable<?>>>getStatisticsSortedSet(Comparator<StatisticsInfoDto<? extends Comparable<?>>> comparator) throws IllegalAccessException;
}
