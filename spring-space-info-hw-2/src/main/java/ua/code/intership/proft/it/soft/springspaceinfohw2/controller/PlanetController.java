package ua.code.intership.proft.it.soft.springspaceinfohw2.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.ReportInfo;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetListRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetReportRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.MessageResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetListResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.PlanetResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.PlanetReportService;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.PlanetService;

@RestController
@RequestMapping("/api/planet")
@RequiredArgsConstructor
public class PlanetController {
    private final PlanetReportService planetReportService;
    private final PlanetService planetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDto createPlanet(@Valid @RequestBody PlanetRequestDto planetRequestDto) {
        Long planetId = planetService.createPlanet(planetRequestDto);

        return MessageResponseDto.builder()
                                 .message("The planet: " + planetRequestDto.name() + " with id: " + planetId + " was created!")
                                 .build();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlanetResponseDto getPlanetById(@NotNull @PathVariable(name = "id") Long planetId) {
        return planetService.getPlanetById(planetId);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto updatePlanetById(@NotNull @PathVariable(name = "id") Long planetId, @Valid @RequestBody PlanetRequestDto planetRequestDto) {
        planetService.updatePlanetById(planetId, planetRequestDto);
        return MessageResponseDto.builder()
                                 .message("The planet by id:" + planetId + " was updated!")
                                 .build();
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto deletePlanetById(@NotNull @PathVariable(name = "id") Long planetId) {
        planetService.deletePlanetById(planetId);
        return MessageResponseDto.builder()
                                 .message("The planet by id:" + planetId + " was deleted!")
                                 .build();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public PlanetListResponseDto getAllPlanets() {
        return planetService.getAllPlanets();
    }

    @PostMapping("_list")
    @ResponseStatus(HttpStatus.OK)
    public PlanetListResponseDto getPlanetPageByPlanetarySystemId(@Valid @RequestBody PlanetListRequestDto planetListRequestDto) {
        return planetService.getPlanetPageByPlanetarySystemId(planetListRequestDto);
    }

    @PostMapping(value = "_report")
    public ResponseEntity<Resource> generatePlanetReportByPlanetarySystemId(@Valid @RequestBody PlanetReportRequestDto planetReportRequestDto) {
        String fileName = "planet_report";

        Resource resource = planetReportService.generatePlanetReportByPlanetarySystemId(planetReportRequestDto, fileName);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + "." + planetReportRequestDto.fileFormat()
                                                                                                                      .toLowerCase());

        return ResponseEntity.ok()
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_OCTET_STREAM)
                             .body(resource);
    }

    @PostMapping("upload")
    @ResponseStatus(HttpStatus.OK)
    public ReportInfo uploadPlanet(@RequestParam("file") MultipartFile file) {
        return planetService.uploadPlanetFromFile(file);
    }
}
