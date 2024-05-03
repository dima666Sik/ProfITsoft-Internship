package ua.code.intership.proft.it.soft.service.exception;
/**
 * Custom exception class for file generation operations.
 * This class extends RuntimeException to allow unchecked exceptions.
 */
public class FileGenerationException extends RuntimeException {
    public FileGenerationException() {
    }

    public FileGenerationException(String message) {
        super(message);
    }

    public FileGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileGenerationException(Throwable cause) {
        super(cause);
    }
}
