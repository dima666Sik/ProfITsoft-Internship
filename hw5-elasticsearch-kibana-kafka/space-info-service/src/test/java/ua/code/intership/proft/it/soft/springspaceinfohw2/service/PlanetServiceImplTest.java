package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.ReportInfo;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.DiameterDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.MassDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetListRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetarySystemRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetListResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Mass;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.SIUnitLength;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.SIUnitMass;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetRepository;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetarySystemRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper.PlanetMapper.planetIntoPlanetResponseDto;

@SpringBootTest
class PlanetServiceImplTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private PlanetRepository planetRepository;
    @Mock
    private PlanetarySystemRepository planetarySystemRepository;
    @InjectMocks
    private PlanetServiceImpl planetService;
    private PlanetRequestDto planetRequestDto;
    private Planet expectedPlanet;

    @BeforeEach
    void setUp() {
        planetRequestDto = PlanetRequestDto.builder()
                                           .id(1L)
                                           .name("Earth")
                                           .massDto(MassDto.builder()
                                                           .value(10.0)
                                                           .unit(SIUnitMass.KILOGRAM)
                                                           .build())
                                           .diameterDto(DiameterDto.builder()
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
    void getPlanetByIdThrowsIllegalStateException() {
        // Given
        Long planetId = planetRequestDto.id();

        when(planetRepository.findById(planetId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalStateException.class,
                () -> planetService.getPlanetById(planetId));
        assertEquals("The planet with id " + planetId + " was not found!", exception.getMessage());
    }

    @Test
    void createPlanet() {
        Planet planet = Planet.builder()
                              .id(1L)
                              .build();
        when(planetRepository.save(any(Planet.class))).thenReturn(planet);

        Long planetId = planetService.createPlanet(planetRequestDto);

        assertEquals(planet.getId(), planetId);
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
        Long planetId = 1L;
        Planet mockPlanet = Planet.builder()
                                  .id(planetId)
                                  .build();

        when(planetRepository.findById(planetId)).thenReturn(Optional.of(mockPlanet));
        doNothing().when(planetRepository)
                   .delete(mockPlanet);

        planetService.deletePlanetById(planetId);

        verify(planetRepository, times(1)).delete(mockPlanet);
    }

    @Test
    void uploadPlanetFromFiles() {
        String fileContent = "{\"id\":1, \"name\": \"testName\", \"hasRings\": \"true\", \"hasMoons\":\"true\", \"atmosphericComposition\":\"atmosphericCompositionTest\"}";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.csv", "text/plain", fileContent.getBytes());
        List<PlanetRequestDto> planetRequestDtoList = List.of(PlanetRequestDto.builder()
                                                                              .id(1L)
                                                                              .name("testName")
                                                                              .hasMoons(true)
                                                                              .hasRings(true)
                                                                              .atmosphericComposition("atmosphericCompositionTest")
                                                                              .planetarySystemRequestDto(PlanetarySystemRequestDto.builder()
                                                                                                                                  .id(1L)
                                                                                                                                  .name("test")
                                                                                                                                  .build())
                                                                              .build());
        assertDoesNotThrow(() ->
                when(objectMapper.readValue(any(byte[].class), any(TypeReference.class))).thenReturn(planetRequestDtoList)
        );

        when(planetarySystemRepository.existsById(1L)).thenReturn(true);

        when(planetRepository.save(any(Planet.class))).thenReturn(Planet.builder()
                                                                        .build());

        assertDoesNotThrow(() -> planetService.uploadPlanetFromFile(multipartFile));

        verify(planetarySystemRepository).existsById(1L);
        verify(planetRepository).save(any(Planet.class));
    }

    @Test
    void testGetPlanetPageByPlanetarySystemId() {
        PlanetListRequestDto planetListRequestDto = PlanetListRequestDto.builder()
                                                                        .idPlanetSystem(1L)
                                                                        .page(0)
                                                                        .size(10)
                                                                        .build();

        when(planetRepository.findByPlanetarySystemId(planetListRequestDto.idPlanetSystem(), PageRequest.of(0, 10)))
                .thenReturn(Page.empty());

        when(planetarySystemRepository.existsById(planetListRequestDto.idPlanetSystem())).thenReturn(true);

        assertDoesNotThrow(() -> {
            PlanetListResponseDto responseDto = planetService.getPlanetPageByPlanetarySystemId(planetListRequestDto);

            assertNotNull(responseDto);
        });
    }

    @Test
    void testGetPlanetPage() {
        PlanetListRequestDto planetListRequestDto = PlanetListRequestDto.builder()
                                                                        .idPlanetSystem(1L)
                                                                        .page(0)
                                                                        .size(10)
                                                                        .build();

        when(planetRepository.findByPlanetarySystemId(planetListRequestDto.idPlanetSystem(), PageRequest.of(0, 10)))
                .thenReturn(Page.empty());

        when(planetarySystemRepository.existsById(planetListRequestDto.idPlanetSystem())).thenReturn(false);

        Throwable exception = assertThrows(IllegalStateException.class,
                () -> planetService.getPlanetPageByPlanetarySystemId(planetListRequestDto));
        assertEquals("The planet system by id: " + planetListRequestDto.idPlanetSystem() + " was not found!", exception.getMessage());
    }

    @Test
    void testGetPlanetPageByPlanetarySystemIdAndName() {
        PlanetListRequestDto planetListRequestDto = PlanetListRequestDto.builder()
                                                                        .idPlanetSystem(1L)
                                                                        .namePlanetSystem("test")
                                                                        .page(0)
                                                                        .size(10)
                                                                        .build();
        when(planetRepository.findByPlanetarySystemIdAndPlanetarySystemName(planetListRequestDto.idPlanetSystem(), planetListRequestDto.namePlanetSystem(), PageRequest.of(0, 10)))
                .thenReturn(Page.empty());

        when(planetarySystemRepository.existsById(planetListRequestDto.idPlanetSystem())).thenReturn(true);

        assertDoesNotThrow(() -> {
            PlanetListResponseDto responseDto = planetService.getPlanetPageByPlanetarySystemId(planetListRequestDto);

            assertNotNull(responseDto);
        });
    }

}