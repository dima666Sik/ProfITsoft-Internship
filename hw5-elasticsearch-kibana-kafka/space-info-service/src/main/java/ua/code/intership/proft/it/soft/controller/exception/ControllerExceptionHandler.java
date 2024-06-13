package ua.code.intership.proft.it.soft.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.code.intership.proft.it.soft.domain.dto.ErrorMessageDto;
import ua.code.intership.proft.it.soft.service.exception.FileGenerationException;
import ua.code.intership.proft.it.soft.service.exception.FileUploadException;
import ua.code.intership.proft.it.soft.service.exception.ReflectExtractDataException;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto resourceNotFoundException(IllegalStateException ex) {
        return ErrorMessageDto.builder()
                              .statusCode(HttpStatus.BAD_REQUEST.value())
                              .timestamp(new Date())
                              .message(ex.getMessage())
                              .build();
    }

    @ExceptionHandler({FileUploadException.class, FileGenerationException.class, ReflectExtractDataException.class, Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDto resourceNotFoundException(Exception ex) {
        return ErrorMessageDto.builder()
                              .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                              .timestamp(new Date())
                              .message(ex.getMessage())
                              .build();
    }
}
