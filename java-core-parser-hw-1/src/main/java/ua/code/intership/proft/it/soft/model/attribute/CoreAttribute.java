package ua.code.intership.proft.it.soft.model.attribute;

import java.util.Map;

public class CoreAttribute {
    private CoreAttribute() {
    }

    public static final Map<String, Class<?>> PLANET_MAP_ATTRIBUTE = Map.of(
            "id", Integer.class,
            "name", String.class,
            "hasRings", Boolean.class,
            "hasMoons", Boolean.class,
            "atmosphericComposition", String.class
    );

    public static Class<?> getCoreAttribute(String attribute) {
        return CoreAttribute.PLANET_MAP_ATTRIBUTE.get(attribute);
    }

    public static boolean isAttributeExist(String attribute) {
        return CoreAttribute.PLANET_MAP_ATTRIBUTE.containsKey(attribute);
    }
}
