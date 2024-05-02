package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import org.springframework.core.io.Resource;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetReportRequestDto;

public interface PlanetReportService {
    Resource generatePlanetReportByPlanetarySystemId(PlanetReportRequestDto planetReportRequestDto);
}
