package ua.code.intership.proft.it.soft.service;

import org.springframework.core.io.Resource;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetReportRequestDto;

public interface PlanetReportService {
    Resource generatePlanetReportByPlanetarySystemId(PlanetReportRequestDto planetReportRequestDto, String fileName);
}
