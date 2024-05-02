package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.DiameterDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.MassDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Mass;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.SIUnitLength;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.SIUnitMass;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper.PlanetMapper.planetIntoPlanetResponseDto;

@SpringBootTest
class PlanetServiceImplTest {
    @Mock
    private PlanetRepository planetRepository;
    @InjectMocks
    private PlanetServiceImpl planetService;
    private PlanetRequestDto planetRequestDto;
    private Planet expectedPlanet;

    @BeforeEach
    void setUp() {
        planetRequestDto = PlanetRequestDto.builder()
                                           .id(1L)
                                           .name("Earth")
                                           .mass(MassDto.builder()
                                                        .value(10.0)
                                                        .unit(SIUnitMass.KILOGRAM)
                                                        .build())
                                           .diameter(DiameterDto.builder()
                                                                .value(10.0)
                                                                .unit(SIUnitLength.KILOMETER)
                                                                .build())
                                           .hasRings(true)
                                           .hasMoons(true)
                                           .atmosphericComposition("Nitrogen, Oxygen")
                                           .build();

        expectedPlanet = Planet.builder()
                               .mass(Mass.builder()
                                         .build())
                               .diameter(Diameter.builder()
                                                 .build())
                               .planetarySystem(PlanetarySystem.builder()
                                                               .build())
                               .hasMoons(false)
                               .hasRings(false)
                               .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPlanetById() {
        // Given
        Long planetId = planetRequestDto.id();

        when(planetRepository.findById(planetId)).thenReturn(Optional.of(expectedPlanet));

        // When
        PlanetResponseDto actualPlanet = planetService.getPlanetById(planetId);

        // Then
        verify(planetRepository, times(1)).findById(planetId);

        assertEquals(planetIntoPlanetResponseDto(expectedPlanet), actualPlanet);
    }

    @Test
    void createPlanet() {
        when(planetRepository.save(any(Planet.class))).thenReturn(new Planet());

        assertDoesNotThrow(() -> planetService.createPlanet(planetRequestDto));

        verify(planetRepository, times(1)).save(any(Planet.class));
    }

    @Test
    void updatePlanetById() {
        Long planetId = planetRequestDto.id();

        when(planetRepository.findById(planetId)).thenReturn(Optional.of(expectedPlanet));
        when(planetRepository.save(any(Planet.class))).thenReturn(expectedPlanet);

        // When
        assertDoesNotThrow(() -> planetService.updatePlanetById(planetId, planetRequestDto));

        // Then
        verify(planetRepository, times(1)).findById(planetId);
        verify(planetRepository, times(1)).save(any(Planet.class));
    }

    @Test
    void deletePlanetById() {
    }

    @Test
    void uploadPlanetFromFiles() {
    }
}