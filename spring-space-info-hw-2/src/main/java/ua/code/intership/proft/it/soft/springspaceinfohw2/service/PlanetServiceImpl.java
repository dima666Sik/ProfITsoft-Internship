package ua.code.intership.proft.it.soft.springspaceinfohw2.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.ReportInfo;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetListRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetListResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper.PlanetMapper;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Mass;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetRepository;
import ua.code.intership.proft.it.soft.springspaceinfohw2.repository.PlanetarySystemRepository;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.exception.FileUploadException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper.PlanetMapper.planetIntoPlanetResponseDto;
import static ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper.PlanetMapper.planetRequestDtoIntoPlanet;
/**
 * Service class for managing planets.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class PlanetServiceImpl implements PlanetService {
    private final PlanetRepository planetRepository;
    private final PlanetarySystemRepository planetarySystemRepository;
    private final ObjectMapper objectMapper;
    /**
     * Creating a new planet based on the provided request data.
     *
     * @param planetRequestDto The request data for creating the planet.
     * @return The ID of the newly created planet.
     */
    @Transactional
    @Override
    public Long createPlanet(PlanetRequestDto planetRequestDto) {
        Planet planet = planetRequestDtoIntoPlanet(planetRequestDto);
        return planetRepository.save(planet)
                               .getId();
    }
    /**
     * Retrieving information about a planet by its ID.
     *
     * @param planetId The ID of the planet to retrieve.
     * @return Information about the planet.
     */
    @Override
    public PlanetResponseDto getPlanetById(Long planetId) {
        Planet planet = getPlanetByIdOrThrow(planetId);

        return planetIntoPlanetResponseDto(planet);
    }
    /**
     * Updating information about a planet based on the provided request data.
     *
     * @param planetId          The ID of the planet to update.
     * @param planetRequestDto  The request data for updating the planet.
     */
    @Transactional
    @Override
    public void updatePlanetById(Long planetId, PlanetRequestDto planetRequestDto) {
        Planet planet = getPlanetByIdOrThrow(planetId);

        if (planetRequestDto.planetarySystemRequestDto() != null) {
            Optional<PlanetarySystem> planetarySystem = planetarySystemRepository.findById(planetRequestDto.planetarySystemRequestDto()
                                                                                                           .id());
            planetarySystem.ifPresent(planet::setPlanetarySystem);
        }

        if (planetRequestDto.massDto() != null) {
            Mass mass = planet.getMass();
            mass.setUnit(planetRequestDto.massDto()
                                         .unit());
            mass.setValue(planetRequestDto.massDto()
                                          .value());
        }

        if (planetRequestDto.diameterDto() != null) {
            Diameter diameter = planet.getDiameter();
            diameter.setUnit(planetRequestDto.diameterDto()
                                             .unit());
            diameter.setValue(planetRequestDto.diameterDto()
                                              .value());
        }

        planet.setName(planetRequestDto.name());
        planet.setHasMoons(planetRequestDto.hasMoons());
        planet.setHasRings(planetRequestDto.hasRings());
        planet.setAtmosphericComposition(planetRequestDto.atmosphericComposition());

        planetRepository.save(planet);
        log.info("The planet with id: " + planet.getId() + " was updated!");
    }

    /**
     * Deleting a planet by its ID.
     *
     * @param planetId The ID of the planet to delete.
     */
    @Transactional
    @Override
    public void deletePlanetById(Long planetId) {
        Planet planet = getPlanetByIdOrThrow(planetId);
        planetRepository.delete(planet);
        log.info("The planet with id: " + planet.getId() + " was deleted!");
    }

    private Planet getPlanetByIdOrThrow(Long planetId) {
        return planetRepository
                .findById(planetId)
                .orElseThrow(() -> new IllegalStateException("The planet with id " + planetId + " was not found!"));
    }
    /**
     * Retrieving a page of planets based on the given planetary system ID.
     *
     * @param planetListRequestDto The request DTO containing the planetary system ID and optional name filter.
     * @return The response DTO containing the paginated list of planets.
     * @throws IllegalStateException If the specified planetary system does not exist.
     */
    @Override
    public PlanetListResponseDto getPlanetPageByPlanetarySystemId(PlanetListRequestDto planetListRequestDto) {
        Long planetarySystemId = planetListRequestDto.idPlanetSystem();

        if (!planetarySystemRepository.existsById(planetarySystemId)) {
            throw new IllegalStateException("The planet system by id: " + planetarySystemId + " was not found!");
        }

        Pageable pageable = PageRequest.of(planetListRequestDto.page(), planetListRequestDto.size());

        Page<Planet> planetListPage;

        Optional<String> namePlanetSystem = Optional.ofNullable(planetListRequestDto.namePlanetSystem());

        if (namePlanetSystem.isPresent()) {
            planetListPage = planetRepository.findByPlanetarySystemIdAndPlanetarySystemName(planetListRequestDto.idPlanetSystem(), namePlanetSystem.get(), pageable);
        } else {
            planetListPage = planetRepository.findByPlanetarySystemId(planetListRequestDto.idPlanetSystem(), pageable);
        }

        return PlanetListResponseDto.builder()
                                    .planetResponseDtoPage(planetListPage.map(PlanetMapper::planetIntoPlanetResponseDto)
                                                                         .toList())
                                    .totalPages(planetListPage.getTotalPages())
                                    .build();
    }
    /**
     * Uploading planets from a file and saves them to the database.
     *
     * @param multipartFile The multipart file containing planet data.
     * @return Information about the upload process.
     * @throws FileUploadException If an error occurs during file upload.
     */
    @Transactional
    @Override
    public ReportInfo uploadPlanetFromFile(MultipartFile multipartFile) {
        try {
            ReportInfo reportInfo = new ReportInfo();
            byte[] fileBytes = multipartFile.getBytes();

            List<PlanetRequestDto> planetRequestDtoList = objectMapper.readValue(fileBytes, new TypeReference<>() {
            });

            planetRequestDtoList.forEach(pRD -> {
                Long planetarySystemId = pRD.planetarySystemRequestDto()
                                            .id();

                if (!planetarySystemRepository.existsById(planetarySystemId)) {
                    reportInfo.incrementNumberOfUnsuccessfulExecutions();
                    log.warn("The planet system by id: {} was not found!", planetarySystemId);
                    return;
                }

                PlanetarySystem planetarySystem = planetarySystemRepository.getReferenceById(planetarySystemId);
                Planet planet = planetRequestDtoIntoPlanet(pRD);
                planet.setPlanetarySystem(planetarySystem);

                handlePlanetSave(planet, reportInfo);
            });

            log.info("The report info was saved and data was uploaded!");

            return reportInfo;
        } catch (IOException e) {
            log.error("The try to upload file was unsuccessful!", e);
            throw new FileUploadException("The try to upload file was unsuccessful!", e);
        }

    }

    @Override
    public PlanetListResponseDto getAllPlanets() {
        Pageable pageable = PageRequest.of(0, 100);
        Page<Planet> planetListPage = planetRepository.findAll(pageable);

        return PlanetListResponseDto.builder()
                                    .planetResponseDtoPage(planetListPage.map(PlanetMapper::planetIntoPlanetResponseDto)
                                                                         .toList())
                                    .totalPages(planetListPage.getTotalPages())
                                    .build();
    }

    private void handlePlanetSave(Planet planet, ReportInfo reportInfo) {
        try {
            planetRepository.save(planet);
            reportInfo.incrementNumberOfSuccessfulExecutions();
        } catch (Exception e) {
            reportInfo.incrementNumberOfUnsuccessfulExecutions();
            log.warn("The planet with name: {} was not saved!", planet.getName());
        }
    }
}
