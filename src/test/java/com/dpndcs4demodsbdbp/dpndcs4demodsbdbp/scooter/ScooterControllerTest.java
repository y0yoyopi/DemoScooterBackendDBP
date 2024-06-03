package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.application.ScooterController;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto.CreateScooterRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto.ScooterResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ScooterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ScooterService scooterService;

    @InjectMocks
    private ScooterController scooterController;

    private ScooterResponseDto scooterResponseDto;
    private CreateScooterRequestDto createScooterRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        scooterResponseDto = new ScooterResponseDto();
        scooterResponseDto.setId(1L);
        scooterResponseDto.setModel("Model A");
        scooterResponseDto.setParkingAreaId(1L);
        scooterResponseDto.setStatus(ScooterStatus.AVAILABLE);

        createScooterRequestDto = new CreateScooterRequestDto();
        createScooterRequestDto.setModel("Model A");
        createScooterRequestDto.setParkingAreaId(1L);
        createScooterRequestDto.setStatus(ScooterStatus.AVAILABLE);
    }

    @Test
    public void testCreateScooter() throws Exception {
        when(scooterService.createScooter(any(CreateScooterRequestDto.class))).thenReturn(scooterResponseDto);

        mockMvc.perform(post("/scooter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createScooterRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    ScooterResponseDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ScooterResponseDto.class);
                    assertEquals(scooterResponseDto.getId(), response.getId());
                    assertEquals(scooterResponseDto.getModel(), response.getModel());
                    assertEquals(scooterResponseDto.getParkingAreaId(), response.getParkingAreaId());
                    assertEquals(scooterResponseDto.getStatus(), response.getStatus());
                });
    }

    @Test
    public void testGetScooterById() throws Exception {
        when(scooterService.getScooterById(anyLong())).thenReturn(scooterResponseDto);

        mockMvc.perform(get("/scooter/{scooterId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    ScooterResponseDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ScooterResponseDto.class);
                    assertEquals(scooterResponseDto.getId(), response.getId());
                    assertEquals(scooterResponseDto.getModel(), response.getModel());
                    assertEquals(scooterResponseDto.getParkingAreaId(), response.getParkingAreaId());
                    assertEquals(scooterResponseDto.getStatus(), response.getStatus());
                });
    }

    @Test
    public void testUpdateScooter() throws Exception {
        when(scooterService.updateScooter(anyLong(), any(CreateScooterRequestDto.class))).thenReturn(scooterResponseDto);

        mockMvc.perform(put("/scooter/{scooterId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createScooterRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    ScooterResponseDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ScooterResponseDto.class);
                    assertEquals(scooterResponseDto.getId(), response.getId());
                    assertEquals(scooterResponseDto.getModel(), response.getModel());
                    assertEquals(scooterResponseDto.getParkingAreaId(), response.getParkingAreaId());
                    assertEquals(scooterResponseDto.getStatus(), response.getStatus());
                });
    }

    @Test
    public void testDeleteScooter() throws Exception {
        mockMvc.perform(delete("/scooter/{scooterId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllScooters() throws Exception {
        Page<ScooterResponseDto> scooterPage = new PageImpl<>(Collections.singletonList(scooterResponseDto), PageRequest.of(0, 10), 1);
        when(scooterService.getAllScooters(any(PageRequest.class))).thenReturn(scooterPage);

        mockMvc.perform(get("/scooter")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Page<ScooterResponseDto> response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), PageImpl.class);
                    assertEquals(1, response.getTotalElements());
                    ScooterResponseDto scooter = response.getContent().get(0);
                    assertEquals(scooterResponseDto.getId(), scooter.getId());
                    assertEquals(scooterResponseDto.getModel(), scooter.getModel());
                    assertEquals(scooterResponseDto.getParkingAreaId(), scooter.getParkingAreaId());
                    assertEquals(scooterResponseDto.getStatus(), scooter.getStatus());
                });
    }
}
