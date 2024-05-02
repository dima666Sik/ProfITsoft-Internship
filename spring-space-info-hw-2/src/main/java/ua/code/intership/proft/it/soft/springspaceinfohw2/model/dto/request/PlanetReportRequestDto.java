package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

public record PlanetReportRequestDto(
        @NonNull
        Long idPlanetSystem,
        @NonNull
        @Min(1)
        Integer pageSize,
        @NotBlank(message = "file format is required")
        @Size(min = 3)
        String fileFormat
) {
}
