package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetarySystemRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper.PlanetarySystemMapper;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetarySystemRepository;

import java.util.List;

import static ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper.PlanetarySystemMapper.planetarySystemRequestDtoIntoPlanetarySystem;

@Service
@RequiredArgsConstructor
public class PlanetarySystemServiceImpl implements PlanetarySystemService {
    private final PlanetarySystemRepository planetarySystemRepository;

    @Override
    public List<PlanetarySystemRequestDto> getAllPlanetarySystems() {
        return planetarySystemRepository.findAll()
                                        .stream()
                                        .map(PlanetarySystemMapper::planetarySystemIntoPlanetarySystemRequestDto)
                                        .toList();
    }

    @Transactional
    @Override
    public Long createPlanetarySystem(PlanetarySystemRequestDto planetarySystemRequestDto) {
        if (planetarySystemRepository.existsByName(planetarySystemRequestDto.name()))
            throw new IllegalStateException("Planetary system with name " + planetarySystemRequestDto.name() + " already exists");

        PlanetarySystem planetarySystem = planetarySystemRequestDtoIntoPlanetarySystem(planetarySystemRequestDto);
        return planetarySystemRepository.save(planetarySystem)
                                        .getId();
    }

    @Transactional
    @Override
    public void updatePlanetarySystem(Long idPlanetarySystem, PlanetarySystemRequestDto planetarySystemRequestDto) {
        PlanetarySystem planetarySystem = planetarySystemRepository.findById(idPlanetarySystem)
                                                                   .orElseThrow(() -> new IllegalStateException("Could not find planetary system by id " + idPlanetarySystem));
        planetarySystem.setName(planetarySystemRequestDto.name());
        planetarySystemRepository.save(planetarySystem);
    }

    @Transactional
    @Override
    public void deletePlanetarySystem(Long planetarySystemId) {
        if (!planetarySystemRepository.existsById(planetarySystemId))
            throw new IllegalStateException("Could not find planetary system by id " + planetarySystemId);

        planetarySystemRepository.deleteById(planetarySystemId);
    }
}
