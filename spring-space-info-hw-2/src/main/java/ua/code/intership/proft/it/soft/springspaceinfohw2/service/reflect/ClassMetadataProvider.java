package ua.code.intership.proft.it.soft.springspaceinfohw2.service.reflect;

import ua.code.intership.proft.it.soft.springspaceinfohw2.service.exception.ReflectExtractDataException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class ClassMetadataProvider {
    private ClassMetadataProvider() {
    }

    public static <T> String[] extractFieldsDataFromGenericEntity(T entity) {
        List<String> values = new ArrayList<>();
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                values.add(value != null ? value.toString() : "");
            } catch (IllegalAccessException e) {
                throw new ReflectExtractDataException("Failed to extract value from field: " + field.getName(), e);
            }
        }

        return values.toArray(new String[0]);
    }
}
