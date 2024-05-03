package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetarySystemRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper.PlanetarySystemMapper;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetarySystemRepository;

import java.util.List;

import static ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper.PlanetarySystemMapper.planetarySystemRequestDtoIntoPlanetarySystem;

/**
 * Service implementation for managing planetary systems.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class PlanetarySystemServiceImpl implements PlanetarySystemService {
    private final PlanetarySystemRepository planetarySystemRepository;

    /**
     * Retrieves all planetary systems.
     *
     * @return a list of {@link PlanetarySystemRequestDto} representing all planetary systems
     */
    @Override
    public List<PlanetarySystemRequestDto> getAllPlanetarySystems() {
        return planetarySystemRepository.findAll()
                                        .stream()
                                        .map(PlanetarySystemMapper::planetarySystemIntoPlanetarySystemRequestDto)
                                        .toList();
    }

    /**
     * Creating a new planetary system.
     *
     * @param planetarySystemRequestDto the DTO containing information about the new planetary system
     * @return the ID of the newly created planetary system
     * @throws IllegalStateException if a planetary system with the same name already exists
     */
    @Transactional
    @Override
    public Long createPlanetarySystem(PlanetarySystemRequestDto planetarySystemRequestDto) {
        if (planetarySystemRepository.existsByName(planetarySystemRequestDto.name())) {
            log.error("Planetary system with name " + planetarySystemRequestDto.name() + " already exists");
            throw new IllegalStateException("Planetary system with name " + planetarySystemRequestDto.name() + " already exists");
        }

        PlanetarySystem planetarySystem = planetarySystemRequestDtoIntoPlanetarySystem(planetarySystemRequestDto);
        return planetarySystemRepository.save(planetarySystem)
                                        .getId();
    }

    /**
     * Updating an existing planetary system.
     *
     * @param idPlanetarySystem         the ID of the planetary system to update
     * @param planetarySystemRequestDto the DTO containing updated information about the planetary system
     * @throws IllegalStateException if the planetary system with the given ID does not exist
     */
    @Transactional
    @Override
    public void updatePlanetarySystem(Long idPlanetarySystem, PlanetarySystemRequestDto planetarySystemRequestDto) {
        PlanetarySystem planetarySystem = planetarySystemRepository.findById(idPlanetarySystem)
                                                                   .orElseThrow(() -> new IllegalStateException("Could not find planetary system by id " + idPlanetarySystem));
        planetarySystem.setName(planetarySystemRequestDto.name());
        planetarySystemRepository.save(planetarySystem);
    }

    /**
     * Deleting a planetary system.
     *
     * @param planetarySystemId the ID of the planetary system to delete
     * @throws IllegalStateException if the planetary system with the given ID does not exist
     */
    @Transactional
    @Override
    public void deletePlanetarySystem(Long planetarySystemId) {
        if (!planetarySystemRepository.existsById(planetarySystemId)) {
            log.error("Could not find planetary system by id " + planetarySystemId);
            throw new IllegalStateException("Could not find planetary system by id " + planetarySystemId);
        }

        planetarySystemRepository.deleteById(planetarySystemId);
    }
}
