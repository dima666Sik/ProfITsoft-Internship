package ua.code.intership.proft.it.soft.springspaceinfohw2.model.attribute;

import java.util.Set;

/**
 * This class contains the core attributes of a planet.
 */
public class PlanetAttribute {
    public static final String ID = "idPlanet";
    public static final String NAME = "namePlanet";
    public static final String HAS_RINGS = "hasPlanetRings";
    public static final String HAS_MOONS = "hasPlanetMoons";
    public static final String ATMOSPHERIC_COMPOSITION = "atmosphericCompositionPlanet";
    public static final String MASS = "massPlanet";
    public static final String DIAMETER = "diameterPlanet";
    public static final String PLANETARY_SYSTEM = "planetarySystem";
    public static final Set<String> PLANET_SET_ATTRIBUTE = Set.of(
            ID,
            NAME,
            HAS_RINGS,
            HAS_MOONS,
            ATMOSPHERIC_COMPOSITION
    );

    private PlanetAttribute() {
    }

    public static boolean isAttributeExist(String attribute) {
        return PLANET_SET_ATTRIBUTE.contains(attribute);
    }
}
