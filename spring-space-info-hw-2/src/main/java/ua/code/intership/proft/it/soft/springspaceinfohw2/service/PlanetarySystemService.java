package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetarySystemRequestDto;

import java.util.List;

public interface PlanetarySystemService {
    List<PlanetarySystemRequestDto> getAllPlanetarySystems();
    Long createPlanetarySystem(PlanetarySystemRequestDto planetarySystemRequestDto);
    void updatePlanetarySystem(Long idPlanetarySystem, PlanetarySystemRequestDto planetarySystemRequestDto);
    void deletePlanetarySystem(Long planetarySystemId);
}
