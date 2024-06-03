package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.application.ParkingAreaController;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingAreaService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.ParkingAreaResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.CreateParkingAreaRequestDto;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingAreaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ParkingAreaService parkingAreaService;

    @InjectMocks
    private ParkingAreaController parkingAreaController;

    private ParkingAreaResponseDto parkingAreaResponseDto;
    private CreateParkingAreaRequestDto createParkingAreaRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        parkingAreaResponseDto = new ParkingAreaResponseDto();
        parkingAreaResponseDto.setId(1L);
        parkingAreaResponseDto.setLatitude(40.7128);
        parkingAreaResponseDto.setLongitude(-74.0060);

        createParkingAreaRequestDto = new CreateParkingAreaRequestDto();
        createParkingAreaRequestDto.setLatitude(40.7128);
        createParkingAreaRequestDto.setLongitude(-74.0060);
    }

    @Test
    public void testCreateParkingArea() throws Exception {
        when(parkingAreaService.createParkingArea(any(CreateParkingAreaRequestDto.class))).thenReturn(parkingAreaResponseDto);

        mockMvc.perform(post("/parking-areas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"latitude\": 40.7128, \"longitude\": -74.0060}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    ParkingAreaResponseDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ParkingAreaResponseDto.class);
                    assertEquals(parkingAreaResponseDto.getId(), response.getId());
                    assertEquals(parkingAreaResponseDto.getLatitude(), response.getLatitude());
                    assertEquals(parkingAreaResponseDto.getLongitude(), response.getLongitude());
                });
    }

    @Test
    public void testGetParkingAreaById() throws Exception {
        when(parkingAreaService.getParkingAreaById(anyLong())).thenReturn(parkingAreaResponseDto);

        mockMvc.perform(get("/parking-areas/{parkingAreaId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    ParkingAreaResponseDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ParkingAreaResponseDto.class);
                    assertEquals(parkingAreaResponseDto.getId(), response.getId());
                    assertEquals(parkingAreaResponseDto.getLatitude(), response.getLatitude());
                    assertEquals(parkingAreaResponseDto.getLongitude(), response.getLongitude());
                });
    }

    @Test
    public void testUpdateParkingArea() throws Exception {
        when(parkingAreaService.updateParkingArea(anyLong(), any(CreateParkingAreaRequestDto.class))).thenReturn(parkingAreaResponseDto);

        mockMvc.perform(put("/parking-areas/{parkingAreaId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"latitude\": 40.7128, \"longitude\": -74.0060}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    ParkingAreaResponseDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ParkingAreaResponseDto.class);
                    assertEquals(parkingAreaResponseDto.getId(), response.getId());
                    assertEquals(parkingAreaResponseDto.getLatitude(), response.getLatitude());
                    assertEquals(parkingAreaResponseDto.getLongitude(), response.getLongitude());
                });
    }

    @Test
    public void testDeleteParkingArea() throws Exception {
        mockMvc.perform(delete("/parking-areas/{parkingAreaId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllParkingAreas() throws Exception {
        Page<ParkingAreaResponseDto> parkingAreaPage = new PageImpl<>(Collections.singletonList(parkingAreaResponseDto), PageRequest.of(0, 10), 1);
        when(parkingAreaService.getAllParkingAreas(any(PageRequest.class))).thenReturn(parkingAreaPage);

        mockMvc.perform(get("/parking-areas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Page<ParkingAreaResponseDto> response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), PageImpl.class);
                    assertEquals(1, response.getTotalElements());
                    ParkingAreaResponseDto parkingArea = response.getContent().get(0);
                    assertEquals(parkingAreaResponseDto.getId(), parkingArea.getId());
                    assertEquals(parkingAreaResponseDto.getLatitude(), parkingArea.getLatitude());
                    assertEquals(parkingAreaResponseDto.getLongitude(), parkingArea.getLongitude());
                });
    }
}
