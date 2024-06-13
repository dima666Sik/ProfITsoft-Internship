package ua.code.intership.proft.it.soft.service.exception;
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
