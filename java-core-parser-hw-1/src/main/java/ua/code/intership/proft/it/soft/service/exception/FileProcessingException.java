package ua.code.intership.proft.it.soft.service.exception;
/**
 * Custom exception class for file processing operations.
 * This class extends RuntimeException to allow unchecked exceptions.
 */
public class FileProcessingException extends RuntimeException {
    public FileProcessingException() {
    }

    public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileProcessingException(Throwable cause) {
        super(cause);
    }
}
