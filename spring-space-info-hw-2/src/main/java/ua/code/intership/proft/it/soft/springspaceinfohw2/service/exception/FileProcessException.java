package ua.code.intership.proft.it.soft.springspaceinfohw2.service.exception;
public class FileProcessException extends RuntimeException {
    public FileProcessException() {
    }

    public FileProcessException(String message) {
        super(message);
    }

    public FileProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileProcessException(Throwable cause) {
        super(cause);
    }
}
