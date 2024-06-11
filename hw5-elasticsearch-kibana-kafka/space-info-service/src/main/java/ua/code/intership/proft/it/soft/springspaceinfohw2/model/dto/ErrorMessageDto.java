package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record ErrorMessageDto (
        int statusCode,
        Date timestamp,
        String message
){
}
