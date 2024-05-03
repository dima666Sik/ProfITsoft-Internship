package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetarySystemRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetarySystemRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlanetarySystemServiceImplTest {
    @Mock
    private PlanetarySystemRepository planetarySystemRepository;

    @InjectMocks
    private PlanetarySystemServiceImpl planetarySystemService;

    @Test
    void getAllPlanetarySystems() {
        // Given
        List<PlanetarySystem> mockPlanetarySystems = List.of(PlanetarySystem.builder()
                                                                            .id(1L)
                                                                            .name("System 1")
                                                                            .build(), PlanetarySystem.builder()
                                                                                                     .id(2L)
                                                                                                     .name("System 2")
                                                                                                     .build());
        when(planetarySystemRepository.findAll()).thenReturn(mockPlanetarySystems);

        // When
        List<PlanetarySystemRequestDto> result = planetarySystemService.getAllPlanetarySystems();

        // Then
        assertEquals(2, result.size());
        assertEquals("System 1", result.get(0)
                                       .name());
        assertEquals("System 2", result.get(1)
                                       .name());
    }

    @Test
    void createPlanetarySystem() {
        PlanetarySystemRequestDto planetarySystemRequestDto = PlanetarySystemRequestDto.builder()
                                                                                       .id(1L)
                                                                                       .name("System 1")
                                                                                       .build();
        when(planetarySystemRepository.existsByName(planetarySystemRequestDto.name())).thenReturn(false);
        when(planetarySystemRepository.save(any(PlanetarySystem.class))).thenReturn(PlanetarySystem.builder()
                                                                                                   .build());
        // When
        planetarySystemService.createPlanetarySystem(planetarySystemRequestDto);

        // Then
        verify(planetarySystemRepository, times(1)).existsByName(any());
        verify(planetarySystemRepository, times(1)).save(any());
    }

    @Test
    void createPlanetarySystemThrowIllegalStateException() {
        PlanetarySystemRequestDto planetarySystemRequestDto = PlanetarySystemRequestDto.builder()
                                                                                       .id(1L)
                                                                                       .name("System 1")
                                                                                       .build();
        when(planetarySystemRepository.existsByName(planetarySystemRequestDto.name())).thenReturn(true);

        Throwable throwable = assertThrows(IllegalStateException.class, () ->
                planetarySystemService.createPlanetarySystem(planetarySystemRequestDto));
        assertEquals("Planetary system with name " + planetarySystemRequestDto.name() + " already exists", throwable.getMessage());

    }

    @Test
    void updatePlanetarySystem() {
        // Given
        Long idPlanetarySystem = 1L;
        PlanetarySystemRequestDto planetarySystemRequestDto = PlanetarySystemRequestDto.builder()
                                                                                       .id(1L)
                                                                                       .name("System 1")
                                                                                       .build();

        when(planetarySystemRepository.findById(idPlanetarySystem))
                .thenReturn(Optional.empty());


        Throwable throwable = assertThrows(IllegalStateException.class, () ->
                planetarySystemService.updatePlanetarySystem(idPlanetarySystem, planetarySystemRequestDto));
        assertEquals("Could not find planetary system by id " + idPlanetarySystem, throwable.getMessage());

    }

    @Test
    void updatePlanetarySystemThrowIllegalStateException() {
        // Given
        Long idPlanetarySystem = 1L;
        PlanetarySystemRequestDto planetarySystemRequestDto = PlanetarySystemRequestDto.builder()
                                                                                       .id(1L)
                                                                                       .name("System 1")
                                                                                       .build();
        PlanetarySystem existingPlanetarySystem = PlanetarySystem.builder()
                                                                 .id(idPlanetarySystem)
                                                                 .name("Old Name")
                                                                 .build();

        when(planetarySystemRepository.findById(idPlanetarySystem))
                .thenReturn(Optional.of(existingPlanetarySystem));
        when(planetarySystemRepository.save(any(PlanetarySystem.class)))
                .thenReturn(existingPlanetarySystem);

        // When
        planetarySystemService.updatePlanetarySystem(idPlanetarySystem, planetarySystemRequestDto);

        // Then
        verify(planetarySystemRepository).findById(idPlanetarySystem);
        verify(planetarySystemRepository).save(existingPlanetarySystem);
        assertEquals("System 1", existingPlanetarySystem.getName());
    }

    @Test
    void deletePlanetarySystem() {
        Long planetarySystemId = 1L;
        PlanetarySystem existingPlanetarySystem = new PlanetarySystem();
        existingPlanetarySystem.setId(planetarySystemId);

        when(planetarySystemRepository.existsById(planetarySystemId)).thenReturn(true);
        doNothing().when(planetarySystemRepository)
                   .deleteById(planetarySystemId);

        // When
        planetarySystemService.deletePlanetarySystem(planetarySystemId);

        // Then
        verify(planetarySystemRepository).existsById(planetarySystemId);
        verify(planetarySystemRepository).deleteById(planetarySystemId);
    }

    @Test
    void deletePlanetarySystemThrowIllegalStateException() {
        Long planetarySystemId = 1L;
        PlanetarySystem existingPlanetarySystem = new PlanetarySystem();
        existingPlanetarySystem.setId(planetarySystemId);

        when(planetarySystemRepository.existsById(planetarySystemId)).thenReturn(false);

        Throwable throwable = assertThrows(IllegalStateException.class, () ->
                planetarySystemService.deletePlanetarySystem(planetarySystemId));
        assertEquals("Could not find planetary system by id " + planetarySystemId, throwable.getMessage());
    }
}