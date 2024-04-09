package ua.code.intership.proft.it.soft.service.statistic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.model.dto.StatisticsInfoDto;
import ua.code.intership.proft.it.soft.service.parser.StringParser;
import ua.code.intership.proft.it.soft.service.parser.TypeParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static ua.code.intership.proft.it.soft.model.attribute.CoreAttribute.*;

public final class PlanetStatisticsProcessor implements StatisticsProcessor {
    private static PlanetStatisticsProcessor instance;
    private static final Set<StatisticsInfoDto<? extends Comparable<?>>> STATISTICS_INFO_DTO_SET;

    static {
        STATISTICS_INFO_DTO_SET = ConcurrentHashMap.newKeySet();
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
            Gson gson = new Gson();

            jsonReader.beginArray();

            while (jsonReader.hasNext()) {
                JsonElement jsonElement = gson.fromJson(jsonReader, JsonElement.class);

                Planet planet = gson.fromJson(jsonElement, Planet.class);

                switch (attribute) {
                    case ID -> processStatistic(planet.getId());
                    case NAME -> processStatistic(planet.getName());
                    case HAS_MOONS -> processStatistic(planet.isHasMoons());
                    case HAS_RINGS -> processStatistic(planet.isHasRings());
                    case ATMOSPHERIC_COMPOSITION -> processStatistic(planet.getAtmosphericComposition());
                    default -> throw new IllegalArgumentException("Unknown attribute: " + attribute);
                }
            }

            jsonReader.endArray();
        }
    }

    private <T extends Comparable<T>> void processStatistic(T valueAttribute){
        if (isStringAttribute(valueAttribute)){
            Set<String> setStringAttributes = getSetStringAttributes(valueAttribute.toString());
            setStringAttributes.forEach(this::addStatistic);
            return;
        }
        addStatistic(valueAttribute);
    }

    private <T> boolean isExistInSetStatistics(T attribute) {
        return STATISTICS_INFO_DTO_SET.stream()
                                      .anyMatch(el -> el.getAttribute()
                                                 .equals(attribute));
    }

    private Set<String> getSetStringAttributes(String strAttribute) {
        if (strAttribute.matches(".*[,#|;].*")) {
            TypeParser<String> parser = new StringParser();
            return parser.parseToSet(strAttribute);
        } else return Set.of(strAttribute);
    }

    private <T extends Comparable<T>> boolean isStringAttribute(T valueAttribute) {
        return valueAttribute instanceof String;
    }

    private synchronized <T extends Comparable<T>> void addStatistic(T valueAttribute) {

        if (isExistInSetStatistics(valueAttribute)) {
            Optional<StatisticsInfoDto<?>> optionalStatisticsDto = STATISTICS_INFO_DTO_SET.stream()
                                                                                          .filter(el -> el.getAttribute()
                                                                                               .equals(valueAttribute))
                                                                                          .findFirst();
            optionalStatisticsDto.ifPresent(StatisticsInfoDto::incrementNumberOfRepetitions);
            return;
        }

        StatisticsInfoDto<? extends Comparable<T>> statisticsInfoDto = StatisticsInfoDto.<T>builder()
                                                                                        .attribute(valueAttribute)
                                                                                        .numberOfRepetitions(1)
                                                                                        .build();
        STATISTICS_INFO_DTO_SET.add(statisticsInfoDto);
    }

    @Override
    public Set<StatisticsInfoDto<? extends Comparable<?>>> getStatisticsSet() throws IllegalAccessException {
        if (STATISTICS_INFO_DTO_SET.isEmpty()) throw new IllegalAccessException("Set hasn't statistics!");
        return STATISTICS_INFO_DTO_SET;
    }

    @Override
    public Set<StatisticsInfoDto<? extends Comparable<?>>> getStatisticsSortedSet(Comparator<StatisticsInfoDto<? extends Comparable<?>>> comparator) throws IllegalAccessException {
        return getStatisticsSet().stream()
                                 .sorted(comparator)
                                 .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
