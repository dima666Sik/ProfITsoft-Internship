package ua.code.intership.proft.it.soft.service.util.constant;

import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.model.StatisticsInfo;
import ua.code.intership.proft.it.soft.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.model.si.data.Mass;
import ua.code.intership.proft.it.soft.model.si.data.SIUnitLength;
import ua.code.intership.proft.it.soft.model.si.data.SIUnitMass;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ua.code.intership.proft.it.soft.service.util.random.Randomize.generateRandomize;

public final class ConstantGenerator {
    private ConstantGenerator() {
    }

    public static final int DEFAULT_COUNT_THREADS_TO_PROCESSING_FILES = 10;

    public static List<Planet> getPlanets(int countPlanets) {
        List<Planet> planets = new ArrayList<>();
        int i = 0;
        while (i < countPlanets) {
            int atmosphericCompositionIndex = generateRandomize(0, 10);
            planets.add(generatePlanet(++i,
                    "Planet" + generateRandomize(0, 1000),
                    generateRandomize(0.01, 10),
                    generateRandomize(4800, 10000),
                    generateRandomize(),
                    generateRandomize(),
                    atmosphericCompositionIndex >= 9 ? "Oxygen, Nitrogen, Carbon dioxide" :
                            atmosphericCompositionIndex >= 7 ? "Oxygen, Nitrogen" :
                                    atmosphericCompositionIndex >= 5 ? "Nitrogen, Carbon dioxide" :
                                            atmosphericCompositionIndex >= 3 ? "Hydrogen, Helium" :
                                                    atmosphericCompositionIndex >= 1 ? "Beryllium" :
                                                            "Magnesium"
            ));
        }
        return planets;
    }

    private static Planet generatePlanet(long id, String name, double massValue, double diameterValue,
                                         boolean hasMoons, boolean hasRings, String atmosphericComposition) {
        int indexPlanetarySystem = generateRandomize(0, 2);
        return Planet.builder()
                     .id(id)
                     .name(name)
                     .mass(Mass.builder()
                               .unit(SIUnitMass.KILOGRAM)
                               .value(massValue)
                               .build())
                     .diameter(Diameter.builder()
                                       .unit(SIUnitLength.KILOMETER)
                                       .value(diameterValue)
                                       .build())
                     .hasMoons(hasMoons)
                     .hasRings(hasRings)
                     .atmosphericComposition(atmosphericComposition)
                     .planetarySystem(generatePlanetarySystem(indexPlanetarySystem, "Planetary System X" + indexPlanetarySystem))
                     .build();
    }

    private static PlanetarySystem generatePlanetarySystem(long id, String name) {
        return PlanetarySystem.builder()
                              .id(id)
                              .name(name)
                              .build();
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
