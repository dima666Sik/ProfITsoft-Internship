package ua.code.intership.proft.it.soft.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ua.code.intership.proft.it.soft.domain.model.Planet;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetReportRequestDto;
import ua.code.intership.proft.it.soft.repository.PlanetRepository;
import ua.code.intership.proft.it.soft.repository.PlanetarySystemRepository;
import ua.code.intership.proft.it.soft.service.exception.FileProcessException;
import ua.code.intership.proft.it.soft.service.report.PlanetReportCreatorFactory;
import ua.code.intership.proft.it.soft.service.report.ReportCreator;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlanetReportServiceImplTest {
    @Mock
    private PlanetRepository planetRepository;
    @Mock
    private ReportCreator<Planet> planetReportCreator;

    @Mock
    private PlanetarySystemRepository planetarySystemRepository;

    @Mock
    private PlanetReportCreatorFactory planetReportCreatorFactory;

    @InjectMocks
    private PlanetReportServiceImpl planetReportService;

    private PlanetReportRequestDto requestDto;
    private final Long planetarySystemId = 1L;

    @BeforeEach
    void setUp() {
        requestDto = PlanetReportRequestDto.builder()
                                           .idPlanetSystem(planetarySystemId)
                                           .fileFormat("csv")
                                           .pageSize(10)
                                           .build();
    }

    @Test
    void generatePlanetReportByPlanetarySystemIdPlanetarySystemExistsReturnsResource() {
        when(planetRepository.findAll()).thenReturn(List.of());
        when(planetarySystemRepository.existsById(planetarySystemId)).thenReturn(true);
        when(planetReportCreatorFactory.getReportCreator("csv")).thenReturn(planetReportCreator);

        assertDoesNotThrow(() -> {
            when(planetReportCreator.createReport(any(), any(), any())).thenReturn(File.createTempFile("/tmp/report", "csv"));
            Resource resource = planetReportService.generatePlanetReportByPlanetarySystemId(requestDto, any());
            assertNotNull(resource);
        });

    }

    @Test
    void generatePlanetReportByPlanetarySystemIdPlanetarySystemNotFoundThrowsIllegalStateException() {
        when(planetRepository.findAll()).thenReturn(List.of());
        when(planetarySystemRepository.existsById(planetarySystemId)).thenReturn(false);

        Throwable exception = assertThrows(IllegalStateException.class,
                () -> planetReportService.generatePlanetReportByPlanetarySystemId(requestDto, ""));
        assertEquals("The planet system by id: " + planetarySystemId + " was not found!", exception.getMessage());
    }

    @Test
    void generatePlanetReportByPlanetarySystemIdPlanetarySystemNotFoundThrowsFileProcessException() {
        when(planetRepository.findAll()).thenReturn(List.of());
        when(planetarySystemRepository.existsById(planetarySystemId)).thenReturn(true);
        when(planetReportCreatorFactory.getReportCreator("csv")).thenReturn(planetReportCreator);
        when(planetReportCreator.createReport(any(), any(), any())).thenReturn(new File(""));

        Throwable exception = assertThrows(FileProcessException.class,
                () -> planetReportService.generatePlanetReportByPlanetarySystemId(requestDto, ""));
        assertEquals("The process file was not successful!", exception.getMessage());
    }
}