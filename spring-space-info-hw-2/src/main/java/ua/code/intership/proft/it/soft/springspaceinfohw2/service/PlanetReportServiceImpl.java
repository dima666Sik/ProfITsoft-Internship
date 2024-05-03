package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.attribute.PlanetAttribute;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetReportRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetRepository;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetarySystemRepository;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.exception.FileProcessException;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.report.ReportCreator;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.report.PlanetReportCreatorFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class PlanetReportServiceImpl implements PlanetReportService {
    private final PlanetRepository planetRepository;
    private final PlanetarySystemRepository planetarySystemRepository;
    private final PlanetReportCreatorFactory planetReportCreatorFactory;

    @Override
    public Resource generatePlanetReportByPlanetarySystemId(PlanetReportRequestDto planetReportRequestDto, String fileName) {
        Long planetarySystemId = planetReportRequestDto.idPlanetSystem();

        if (!planetarySystemRepository.existsById(planetarySystemId)) {
            throw new IllegalStateException("The planet system by id: " + planetarySystemId + " was not found!");
        }

        String fileFormat = planetReportRequestDto.fileFormat();

        ReportCreator<Planet> reportCreator = planetReportCreatorFactory.getReportCreator(fileFormat);

        File reportFile = generatePlanetReportFile(reportCreator, fileName, planetReportRequestDto.pageSize());

        try {
            return new InputStreamResource(new FileInputStream(reportFile));
        } catch (FileNotFoundException e) {
            throw new FileProcessException("The process file was not successful!", e);
        }
    }

    private File generatePlanetReportFile(ReportCreator<Planet> planetReportCreator, String fileName, int pageSize) {
        String[] columnPlanetTitles = new String[]{PlanetAttribute.ID, PlanetAttribute.NAME, PlanetAttribute.HAS_RINGS,
                PlanetAttribute.HAS_MOONS, PlanetAttribute.ATMOSPHERIC_COMPOSITION,
                PlanetAttribute.MASS, PlanetAttribute.DIAMETER, PlanetAttribute.PLANETARY_SYSTEM};

        return planetReportCreator.createReport(pageNumber -> {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return planetRepository.findAll(pageable);
        }, fileName, columnPlanetTitles);
    }
}
