package ua.code.intership.proft.it.soft.service.util.reflect;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * A utility class for providing interact with metadata about classes and their fields.
 */
public final class ClassMetadataProvider {
    private ClassMetadataProvider() {
    }

    /**
     * Uses reflection to initialize the mock state of a service object.
     *
     * @param service the service object to initialize
     * @param stateName the name of the mock state field
     * @param requiredValue the required value for the mock state field
     * @throws NoSuchFieldException if the service object does not have a field with the specified name
     * @throws IllegalAccessException if the field cannot be accessed
     */
    public static <T, V> void reflectInitMockStateForService(T service, String stateName, V requiredValue)
            throws NoSuchFieldException, IllegalAccessException {
        var coreServiceClass = service.getClass();
        var fieldStateServiceClass = coreServiceClass.getDeclaredField(stateName);
        fieldStateServiceClass.setAccessible(true);
        fieldStateServiceClass.set(service, requiredValue);
    }
}
