package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response;

import lombok.Builder;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.DiameterDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.MassDto;

@Builder
public record PlanetResponseDto(
        Long id,
        String name,
        Boolean hasRings,
        Boolean hasMoons,
        String atmosphericComposition,
        MassDto massDto,
        DiameterDto diameterDto,
        PlanetarySystemResponseDto planetarySystemResponseDto
) {
}
