package ua.code.intership.proft.it.soft.service.statistic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.model.dto.StatisticsDto;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class PlanetStatisticsProcessor implements StatisticsProcessor {
    private static PlanetStatisticsProcessor instance;
    private static final Set<StatisticsDto> statisticsDtoSet;

    static {
        statisticsDtoSet = ConcurrentHashMap.newKeySet();
    }

    private PlanetStatisticsProcessor() {
    }

    public static PlanetStatisticsProcessor getInstance() {
        if (instance != null) return instance;
        return new PlanetStatisticsProcessor();
    }

    @Override
    public void collectStatistics(File file, String attribute) throws IOException {
        try (JsonReader jsonReader = new JsonReader(new FileReader(file.getPath()))) {
            TypeToken<ArrayList<Planet>> type = new TypeToken<>() {
            };
            Gson gson = new Gson();
            ArrayList<Planet> planets = gson.fromJson(jsonReader, type);
            for (Planet planet : planets) {
                addStatistic(planet.getAtmosphericComposition());
            }
        }

    }

    private boolean isExistInSetStatistics(String attribute) {
        return statisticsDtoSet.stream()
                               .anyMatch(el -> el.getAttribute()
                                                 .equals(attribute));
    }

    private void addStatistic(String valueAttribute) {
        if (isExistInSetStatistics(valueAttribute)) {
            Optional<StatisticsDto> optionalStatisticsDto = statisticsDtoSet.stream()
                                                                            .filter(el -> el.getAttribute()
                                                                                            .equals(valueAttribute))
                                                                            .findFirst();
            optionalStatisticsDto.ifPresent(StatisticsDto::incrementNumberOfRepetitions);
            return;
        }

        StatisticsDto statisticsDto = StatisticsDto.builder()
                                                   .attribute(valueAttribute)
                                                   .numberOfRepetitions(1)
                                                   .build();
        statisticsDtoSet.add(statisticsDto);
    }

    @Override
    public Set<StatisticsDto> getStatisticsSet() throws IllegalAccessException {
        if (statisticsDtoSet.isEmpty()) throw new IllegalAccessException("Set has no statistics!");
        return statisticsDtoSet;
    }

    @Override
    public Set<StatisticsDto> getStatisticsSortedSet(Comparator<StatisticsDto> comparator) throws IllegalAccessException {
        return getStatisticsSet().stream()
                               .sorted(comparator)
                               .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
