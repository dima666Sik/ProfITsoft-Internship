package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import org.springframework.web.multipart.MultipartFile;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.ReportInfo;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetListRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetListResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetResponseDto;

public interface PlanetService {
    Long createPlanet(PlanetRequestDto planetRequestDto);
    PlanetResponseDto getPlanetById(Long planetId);
    void updatePlanetById(Long planetId, PlanetRequestDto planetRequestDto);
    void deletePlanetById(Long planetId);
    PlanetListResponseDto getPlanetPageByPlanetarySystemId(PlanetListRequestDto planetListRequestDto);
    ReportInfo uploadPlanetFromFile(MultipartFile multipartFile);
}
