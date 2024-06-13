package ua.code.intership.proft.it.soft.domain.mapper;

import ua.code.intership.proft.it.soft.domain.dto.MassDto;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetRequestDto;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetarySystemRequestDto;
import ua.code.intership.proft.it.soft.domain.dto.response.PlanetResponseDto;
import ua.code.intership.proft.it.soft.domain.dto.response.PlanetarySystemResponseDto;
import ua.code.intership.proft.it.soft.domain.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.domain.model.si.data.Mass;
import ua.code.intership.proft.it.soft.domain.model.Planet;
import ua.code.intership.proft.it.soft.domain.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.domain.dto.DiameterDto;

public final class PlanetMapper {
    private PlanetMapper() {
    }

    public static Planet planetRequestDtoIntoPlanet(PlanetRequestDto planetRequestDto) {
        PlanetarySystem planetarySystem = null;
        if (planetRequestDto.planetarySystemRequestDto() != null) {
            planetarySystem = PlanetarySystem.builder()
                                             .id(planetRequestDto.planetarySystemRequestDto()
                                                                 .id())
                                             .name(planetRequestDto.planetarySystemRequestDto()
                                                                   .name())
                                             .build();
        }

        Mass mass = null;
        if (planetRequestDto.massDto() != null) {
            mass = PlanetMapper.createMass(planetRequestDto.massDto());
        }

        Diameter diameter = null;
        if (planetRequestDto.diameterDto() != null) {
            diameter = PlanetMapper.createDiameter(planetRequestDto.diameterDto());
        }

        return Planet.builder()
                     .name(planetRequestDto.name())
                     .hasMoons(planetRequestDto.hasMoons())
                     .hasRings(planetRequestDto.hasRings())
                     .mass(mass)
                     .diameter(diameter)
                     .atmosphericComposition(planetRequestDto.atmosphericComposition())
                     .planetarySystem(planetarySystem)
                     .build();
    }

    public static PlanetResponseDto planetIntoPlanetResponseDto(Planet planet) {
        PlanetarySystemResponseDto planetarySystemResponseDto = null;
        if (planet.getPlanetarySystem() != null) {
            planetarySystemResponseDto = PlanetarySystemResponseDto.builder()
                                                                   .id(planet.getPlanetarySystem()
                                                                             .getId())
                                                                   .name(planet.getPlanetarySystem()
                                                                               .getName())
                                                                   .build();
        }

        MassDto massDto = null;
        if (planet.getMass() != null) {
            massDto = PlanetMapper.createMassDto(planet.getMass());
        }

        DiameterDto diameterDto = null;
        if (planet.getDiameter() != null) {
            diameterDto = PlanetMapper.createDiameterDto(planet.getDiameter());
        }

        return PlanetResponseDto.builder()
                                .id(planet.getId())
                                .name(planet.getName())
                                .hasMoons(planet.getHasMoons())
                                .hasRings(planet.getHasRings())
                                .atmosphericComposition(planet.getAtmosphericComposition())
                                .massDto(massDto)
                                .diameterDto(diameterDto)
                                .planetarySystemResponseDto(planetarySystemResponseDto)
                                .build();
    }

    public static PlanetRequestDto planetIntoPlanetRequestDto(Planet planet) {
        PlanetarySystemRequestDto planetarySystemRequestDto = null;
        if (planet.getPlanetarySystem() != null) {
            planetarySystemRequestDto = PlanetarySystemRequestDto.builder()
                                                                 .id(planet.getPlanetarySystem()
                                                                           .getId())
                                                                 .name(planet.getPlanetarySystem()
                                                                             .getName())
                                                                 .build();
        }

        MassDto massDto = null;
        if (planet.getMass() != null) {
            massDto = PlanetMapper.createMassDto(planet.getMass());
        }

        DiameterDto diameterDto = null;
        if (planet.getDiameter() != null) {
            diameterDto = PlanetMapper.createDiameterDto(planet.getDiameter());
        }

        return PlanetRequestDto.builder()
                               .id(planet.getId())
                               .name(planet.getName())
                               .hasMoons(planet.getHasMoons())
                               .hasRings(planet.getHasRings())
                               .atmosphericComposition(planet.getAtmosphericComposition())
                               .massDto(massDto)
                               .diameterDto(diameterDto)
                               .planetarySystemRequestDto(planetarySystemRequestDto)
                               .build();
    }


    private static Mass createMass(MassDto massRequestDto) {
        return Mass.builder()
                   .unit(massRequestDto.unit())
                   .value(massRequestDto.value())
                   .build();
    }

    private static Diameter createDiameter(DiameterDto diameterRequestDto) {
        return Diameter.builder()
                       .unit(diameterRequestDto.unit())
                       .value(diameterRequestDto.value())
                       .build();
    }

    private static MassDto createMassDto(Mass mass) {
        return MassDto.builder()
                      .unit(mass.getUnit())
                      .value(mass.getValue())
                      .build();
    }

    private static DiameterDto createDiameterDto(Diameter diameter) {
        return DiameterDto.builder()
                          .unit(diameter.getUnit())
                          .value(diameter.getValue())
                          .build();
    }

}
