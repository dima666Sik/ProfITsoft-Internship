package ua.code.intership.proft.it.soft.service.statistic;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.model.dto.StatisticsInfoDto;
import ua.code.intership.proft.it.soft.service.parser.StringParser;
import ua.code.intership.proft.it.soft.service.parser.TypeParser;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory jsonFactory = new JsonFactory();
        try (JsonParser jsonParser = jsonFactory.createParser(file)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalArgumentException("JSON data should start with an array");
            }

            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

                Planet planet = objectMapper.readValue(jsonParser, Planet.class);

                switch (attribute) {
                    case "id" -> processStatistic(planet.getId());
                    case "name" -> processStatistic(planet.getName());
                    case "hasMoons" -> processStatistic(planet.isHasMoons());
                    case "hasRings" -> processStatistic(planet.isHasRings());
                    case "atmosphericComposition" -> processStatistic(planet.getAtmosphericComposition());
                    default -> throw new IllegalArgumentException("Unknown attribute: " + attribute);
                }
            }
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
