package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record PlanetListResponseDto(
        @JsonProperty("list") List<PlanetResponseDto> planetResponseDtoPage,
        Integer totalPages
) {
}
