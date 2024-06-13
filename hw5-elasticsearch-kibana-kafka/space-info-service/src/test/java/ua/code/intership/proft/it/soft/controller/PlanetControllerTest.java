package ua.code.intership.proft.it.soft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import ua.code.intership.proft.it.soft.SpringSpaceInfoHw2Application;
import ua.code.intership.proft.it.soft.domain.model.ReportInfo;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetListRequestDto;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetReportRequestDto;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetRequestDto;
import ua.code.intership.proft.it.soft.domain.dto.response.PlanetListResponseDto;
import ua.code.intership.proft.it.soft.domain.dto.response.PlanetResponseDto;
import ua.code.intership.proft.it.soft.service.PlanetReportService;
import ua.code.intership.proft.it.soft.service.PlanetService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SpringSpaceInfoHw2Application.class)
@AutoConfigureMockMvc
class PlanetControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlanetService planetService;
    @MockBean
    private PlanetReportService planetReportService;

    @SneakyThrows
    @Test
    void createPlanet() {
        Long createdId = 1L;
        PlanetRequestDto planetRequestDto = PlanetRequestDto.builder()
                                                            .id(createdId)
                                                            .name("Test")
                                                            .hasRings(true)
                                                            .hasMoons(true)
                                                            .atmosphericComposition("Test A")
                                                            .build();
        when(planetService.createPlanet(any(PlanetRequestDto.class))).thenReturn(createdId);

        mockMvc.perform(post("/api/planet")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(planetRequestDto)))
               .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    void getPlanetById() {
        Long planetId = 1L;
        PlanetResponseDto planetResponseDto = PlanetResponseDto.builder()
                                                               .id(planetId)
                                                               .name("Test")
                                                               .hasRings(true)
                                                               .hasMoons(true)
                                                               .atmosphericComposition("Test A")
                                                               .build();
        when(planetService.getPlanetById(any(Long.class))).thenReturn(planetResponseDto);

        mockMvc.perform(get("/api/planet/{id}", planetId))
               .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void updatePlanetById() {
        Long id = 1L;
        PlanetRequestDto planetRequestDto = PlanetRequestDto.builder()
                                                            .id(id)
                                                            .name("Test")
                                                            .hasRings(true)
                                                            .hasMoons(true)
                                                            .atmosphericComposition("Test A")
                                                            .build();
        doNothing().when(planetService)
                   .updatePlanetById(any(Long.class), any(PlanetRequestDto.class));

        mockMvc.perform(put("/api/planet/{id}", id)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(planetRequestDto)))
               .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void deletePlanetById() {
        Long id = 1L;
        doNothing().when(planetService)
                   .deletePlanetById(any(Long.class));

        mockMvc.perform(delete("/api/planet/{id}", id))
               .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void getPlanetPageByPlanetarySystemId() {
        PlanetListRequestDto planetListRequestDto = PlanetListRequestDto.builder()
                                                                        .idPlanetSystem(1L)
                                                                        .page(0)
                                                                        .size(3)
                                                                        .build();
        when(planetService.getPlanetPageByPlanetarySystemId(any(PlanetListRequestDto.class)))
                .thenReturn(PlanetListResponseDto.builder()
                                                 .planetResponseDtoPage(List.of())
                                                 .totalPages(3)
                                                 .build());

        mockMvc.perform(post("/api/planet/_list")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(planetListRequestDto)))
               .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void generatePlanetReportByPlanetarySystemId() {
        // Mocking service response
        byte[] fileContent = "Test report content".getBytes();
        Resource resource = new ByteArrayResource(fileContent);
        when(planetReportService.generatePlanetReportByPlanetarySystemId(any(PlanetReportRequestDto.class), any(String.class)))
                .thenReturn(resource);

        // Perform POST request
        mockMvc.perform(post("/api/planet/_report")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(PlanetReportRequestDto.builder()
                                                                                      .idPlanetSystem(1L)
                                                                                      .pageSize(1)
                                                                                      .fileFormat("csv")
                                                                                      .build())))
               .andExpect(status().isOk())
               .andExpect(header().string("Content-Disposition", "attachment; filename=planet_report.csv"))
               .andExpect(content().bytes(fileContent));
    }

    @SneakyThrows
    @Test
    void uploadPlanet() {
        when(planetService.uploadPlanetFromFile(any(MultipartFile.class)))
                .thenReturn(new ReportInfo());

        byte[] content = "Test file content".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "test-file.csv", "text/plain", content);

        mockMvc.perform(multipart("/api/planet/upload")
                       .file(file))
               .andExpect(status().isOk());
    }
}