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

@Service
@RequiredArgsConstructor
@Log4j2
public class PlanetServiceImpl implements PlanetService {
    private final PlanetRepository planetRepository;
    private final PlanetarySystemRepository planetarySystemRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public Long createPlanet(PlanetRequestDto planetRequestDto) {
        Planet planet = planetRequestDtoIntoPlanet(planetRequestDto);
        return planetRepository.save(planet)
                               .getId();
    }

    @Override
    public PlanetResponseDto getPlanetById(Long planetId) {
        Planet planet = getPlanetByIdOrThrow(planetId);

        return planetIntoPlanetResponseDto(planet);
    }

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
    }


    @Transactional
    @Override
    public void deletePlanetById(Long planetId) {
        Planet planet = getPlanetByIdOrThrow(planetId);
        planetRepository.delete(planet);
    }

    private Planet getPlanetByIdOrThrow(Long planetId) {
        return planetRepository
                .findById(planetId)
                .orElseThrow(() -> new IllegalStateException("The planet with id " + planetId + " was not found!"));
    }

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

            return reportInfo;
        } catch (IOException e) {
            throw new FileUploadException("The try to upload file was unsuccessful!", e);
        }

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
