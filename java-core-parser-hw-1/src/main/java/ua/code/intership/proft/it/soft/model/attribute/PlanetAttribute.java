package ua.code.intership.proft.it.soft.model.attribute;

import java.util.Set;
/**
 * This class contains the core attributes of a planet.
 */
public class PlanetAttribute {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String HAS_RINGS = "hasRings";
    public static final String HAS_MOONS = "hasMoons";
    public static final String ATMOSPHERIC_COMPOSITION = "atmosphericComposition";

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
