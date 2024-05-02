package ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper;

import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.DiameterDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.MassDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetarySystemRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetarySystemResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Mass;

import java.util.Optional;

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
