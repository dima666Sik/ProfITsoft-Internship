package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.DiameterDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.MassDto;
@Builder
public record PlanetRequestDto(
        Long id,
        @NotBlank(message = "name for Planet is required")
        @Size(min=3, max=60)
        String name,
        @NonNull
        Boolean hasRings,
        @NonNull
        Boolean hasMoons,
        @NotBlank(message = "Atmospheric Composition for Planet required")
        @Size(min=3, max=120)
        String atmosphericComposition,
        @JsonProperty("mass") MassDto massDto,
        @JsonProperty("diameter") DiameterDto diameterDto,
        @JsonProperty("planetarySystem") PlanetarySystemRequestDto planetarySystemRequestDto
) {
}
