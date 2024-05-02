package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response;

import lombok.Builder;

@Builder
public record MessageResponseDto(
        String message
) {
}
