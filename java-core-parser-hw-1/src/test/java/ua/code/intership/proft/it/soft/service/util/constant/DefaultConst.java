package ua.code.intership.proft.it.soft.service.util.constant;

import ua.code.intership.proft.it.soft.model.StatisticsInfo;

import java.util.Set;

public final class DefaultConst {
    private DefaultConst() {
    }
    public static final Set<StatisticsInfo<? extends Comparable<?>>> DEFAULT_STATISTICS_INFO_SET
            = Set.of(
            StatisticsInfo.<String>builder()
                          .attribute("Earth")
                          .numberOfRepetitions(110)
                          .build(),
            StatisticsInfo.<String>builder()
                          .attribute("Mars")
                          .numberOfRepetitions(390)
                          .build(),
            StatisticsInfo.<String>builder()
                          .attribute("Venus")
                          .numberOfRepetitions(3780)
                          .build()
    );
}
