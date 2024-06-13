package ua.code.intership.proft.it.soft.domain.dto.response;

import lombok.Builder;

@Builder
public record PlanetarySystemResponseDto(
        Long id,
        String name
) {
}
