package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

public record PlanetListRequestDto(
        @NonNull
        Long idPlanetSystem,
        @NotBlank(message = "name for PlanetarySystem is required")
        @Size(min = 3, max = 60)
        String namePlanetSystem,
        @NonNull
        @Min(0)
        Integer page,
        @NonNull
        @Min(1)
        Integer size
) {
}
