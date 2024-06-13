package ua.code.intership.proft.it.soft.domain.mapper;

import ua.code.intership.proft.it.soft.domain.dto.request.PlanetarySystemRequestDto;
import ua.code.intership.proft.it.soft.domain.model.PlanetarySystem;

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
