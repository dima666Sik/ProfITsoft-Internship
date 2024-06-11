package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PlanetarySystemRequestDto(
        Long id,
        @NotBlank(message = "name for PlanetarySystem is required")
        @Size(min=3, max=60)
        String name
) {
}