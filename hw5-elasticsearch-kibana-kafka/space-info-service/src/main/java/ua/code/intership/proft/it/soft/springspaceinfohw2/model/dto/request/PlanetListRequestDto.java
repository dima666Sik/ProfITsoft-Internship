package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request;


import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record PlanetListRequestDto(
        @NonNull
        Long idPlanetSystem,

        String namePlanetSystem,
        @NonNull
        @Min(0)
        Integer page,
        @NonNull
        @Min(1)
        Integer size
) {
}
