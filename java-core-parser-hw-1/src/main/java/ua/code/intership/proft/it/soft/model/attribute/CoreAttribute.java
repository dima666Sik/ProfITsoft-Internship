package ua.code.intership.proft.it.soft.model.attribute;

import java.util.Set;

public class CoreAttribute {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String HAS_RINGS = "hasRings";
    public static final String HAS_MOONS = "hasMoons";
    public static final String ATMOSPHERIC_COMPOSITION = "atmosphericComposition";

    public static final Set<String> PLANET_MAP_ATTRIBUTE = Set.of(
            ID,
            NAME,
            HAS_RINGS,
            HAS_MOONS,
            ATMOSPHERIC_COMPOSITION
    );

    private CoreAttribute() {
    }

    public static boolean isAttributeExist(String attribute) {
        return PLANET_MAP_ATTRIBUTE.contains(attribute);
    }
}
