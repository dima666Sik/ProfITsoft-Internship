package ua.code.intership.proft.it.soft.springspaceinfohw2.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetarySystemRequestDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.response.MessageResponseDto;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.PlanetarySystemService;

import java.util.List;

@RestController
@RequestMapping("/api/planetary-system")
@RequiredArgsConstructor
public class PlanetarySystemController {
    private final PlanetarySystemService planetarySystemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDto createPlanetarySystem(@Valid @RequestBody PlanetarySystemRequestDto planetarySystemRequestDto) {
        Long planetarySystemId = planetarySystemService.createPlanetarySystem(planetarySystemRequestDto);
        return MessageResponseDto.builder()
                                 .message("The planetary system:" + planetarySystemRequestDto.name() + " with id: " + planetarySystemId + " was created!")
                                 .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlanetarySystemRequestDto> getAllPlanetarySystem() {
        return planetarySystemService.getAllPlanetarySystems();
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto updatePlanetarySystem(@NotNull @PathVariable(name = "id") Long idPlanetarySystem, @Valid @RequestBody PlanetarySystemRequestDto planetarySystemRequestDto) {
        planetarySystemService.updatePlanetarySystem(idPlanetarySystem, planetarySystemRequestDto);
        return MessageResponseDto.builder()
                                 .message("The planetary system:" + planetarySystemRequestDto.name() + " by id: " + idPlanetarySystem + " was updated!")
                                 .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto deletePlanetarySystem(@NotNull @PathVariable Long id) {
        planetarySystemService.deletePlanetarySystem(id);
        return MessageResponseDto.builder()
                                 .message("The planetary system by id: " + id + " was deleted!")
                                 .build();
    }
}
