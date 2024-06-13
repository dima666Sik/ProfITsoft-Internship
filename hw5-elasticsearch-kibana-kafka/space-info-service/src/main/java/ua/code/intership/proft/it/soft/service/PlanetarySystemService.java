package ua.code.intership.proft.it.soft.service;

import ua.code.intership.proft.it.soft.domain.dto.request.PlanetarySystemRequestDto;

import java.util.List;

public interface PlanetarySystemService {
    List<PlanetarySystemRequestDto> getAllPlanetarySystems();
    Long createPlanetarySystem(PlanetarySystemRequestDto planetarySystemRequestDto);
    void updatePlanetarySystem(Long idPlanetarySystem, PlanetarySystemRequestDto planetarySystemRequestDto);
    void deletePlanetarySystem(Long planetarySystemId);
}
