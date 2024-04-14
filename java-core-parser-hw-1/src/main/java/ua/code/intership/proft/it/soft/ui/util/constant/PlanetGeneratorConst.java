package ua.code.intership.proft.it.soft.ui.util.constant;

import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.model.si.data.Mass;
import ua.code.intership.proft.it.soft.model.si.data.SIUnitLength;
import ua.code.intership.proft.it.soft.model.si.data.SIUnitMass;
import ua.code.intership.proft.it.soft.ui.util.random.Randomize;

import java.util.ArrayList;
import java.util.List;

import static ua.code.intership.proft.it.soft.ui.util.random.Randomize.generateRandomize;

public final class PlanetGeneratorConst {
    private PlanetGeneratorConst() {
    }
    public static final List<List<Planet>> PLANET_LISTS = List.of(
            getPlanets(generateRandomize(5, 10)),
            getPlanets(generateRandomize(7, 12)),
            getPlanets(generateRandomize(3, 8))
    );

    public static List<Planet> getPlanets(int countPlanets) {
        List<Planet> planets = new ArrayList<>();
        int i = 0;
        while (i < countPlanets) {
            int atmosphericCompositionIndex = Randomize.generateRandomize(0, 10);
            planets.add(generatePlanet(++i,
                    "Planet" + Randomize.generateRandomize(0, 1000),
                    Randomize.generateRandomize(0.01, 10),
                    Randomize.generateRandomize(4800, 10000),
                    Randomize.generateRandomize(),
                    Randomize.generateRandomize(),
                    generateRandomByAtmosphericCompositionIndex(atmosphericCompositionIndex)
            ));
        }
        return planets;
    }

    private static String generateRandomByAtmosphericCompositionIndex(int atmosphericCompositionIndex) {
        return atmosphericCompositionIndex >= 9 ? "Oxygen, Nitrogen, Carbon dioxide" :
                atmosphericCompositionIndex >= 7 ? "Oxygen, Nitrogen" :
                        atmosphericCompositionIndex >= 5 ? "Nitrogen, Carbon dioxide" :
                                atmosphericCompositionIndex >= 3 ? "Hydrogen, Helium" :
                                        atmosphericCompositionIndex >= 1 ? "Beryllium" :
                                                "Magnesium";
    }

    private static Planet generatePlanet(long id, String name, double massValue, double diameterValue,
                                         boolean hasMoons, boolean hasRings, String atmosphericComposition) {
        int indexPlanetarySystem = Randomize.generateRandomize(0, 2);
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


}
