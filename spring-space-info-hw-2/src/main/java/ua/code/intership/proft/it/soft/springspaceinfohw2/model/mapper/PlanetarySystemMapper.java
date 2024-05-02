package ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper;

import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetarySystemRequestDto;

public final class PlanetarySystemMapper {
    private PlanetarySystemMapper() {
    }

    public static PlanetarySystem planetarySystemRequestDtoIntoPlanetarySystem(PlanetarySystemRequestDto planetarySystemRequestDto) {

        return PlanetarySystem.builder()
                              .id(planetarySystemRequestDto.id())
                              .name(planetarySystemRequestDto.name())
                              .build();
    }

    public static PlanetarySystemRequestDto planetarySystemIntoPlanetarySystemRequestDto(PlanetarySystem planetarySystem) {
        return PlanetarySystemRequestDto.builder()
                                        .id(planetarySystem.getId())
                                        .name(planetarySystem.getName())
                                        .build();
    }
}
