package ua.code.intership.proft.it.soft.service;

import org.springframework.web.multipart.MultipartFile;
import ua.code.intership.proft.it.soft.domain.model.ReportInfo;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetListRequestDto;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetRequestDto;
import ua.code.intership.proft.it.soft.domain.dto.response.PlanetListResponseDto;
import ua.code.intership.proft.it.soft.domain.dto.response.PlanetResponseDto;

public interface PlanetService {
    Long createPlanet(PlanetRequestDto planetRequestDto);
    PlanetResponseDto getPlanetById(Long planetId);
    void updatePlanetById(Long planetId, PlanetRequestDto planetRequestDto);
    void deletePlanetById(Long planetId);
    PlanetListResponseDto getPlanetPageByPlanetarySystemId(PlanetListRequestDto planetListRequestDto);
    ReportInfo uploadPlanetFromFile(MultipartFile multipartFile);
}
