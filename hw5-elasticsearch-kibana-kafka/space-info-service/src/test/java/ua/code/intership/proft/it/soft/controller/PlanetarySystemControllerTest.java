package ua.code.intership.proft.it.soft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.code.intership.proft.it.soft.SpringSpaceInfoHw2Application;
import ua.code.intership.proft.it.soft.domain.dto.request.PlanetarySystemRequestDto;
import ua.code.intership.proft.it.soft.service.PlanetarySystemService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SpringSpaceInfoHw2Application.class)
@AutoConfigureMockMvc
class PlanetarySystemControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlanetarySystemService planetarySystemService;

    @SneakyThrows
    @Test
    void createPlanetarySystem() {
        PlanetarySystemRequestDto requestDto = PlanetarySystemRequestDto.builder()
                                                                        .name("Test Planetary System")
                                                                        .build();

        Long createdId = 1L;
        when(planetarySystemService.createPlanetarySystem(any(PlanetarySystemRequestDto.class))).thenReturn(createdId);

        mockMvc.perform(post("/api/planetary-system")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    void getAllPlanetarySystem() {
        mockMvc.perform(get("/api/planetary-system"))
               .andExpect(status().is2xxSuccessful());
    }

    @SneakyThrows
    @Test
    void updatePlanetarySystem() {
        Long id = 1L;
        PlanetarySystemRequestDto requestDto = PlanetarySystemRequestDto.builder()
                                                                        .name("Updated Planetary System1")
                                                                        .build();
        doNothing().when(planetarySystemService).updatePlanetarySystem(any(Long.class), any(PlanetarySystemRequestDto.class));

        mockMvc.perform(put("/api/planetary-system/{id}", id)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void deletePlanetarySystem() {
        Long id = 1L;

        doNothing().when(planetarySystemService).deletePlanetarySystem(id);

        mockMvc.perform(delete("/api/planetary-system/{id}", id))
               .andExpect(status().is2xxSuccessful());
    }
}