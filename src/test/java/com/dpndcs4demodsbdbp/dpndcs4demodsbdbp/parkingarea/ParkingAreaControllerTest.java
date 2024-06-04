package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.application.ParkingAreaController;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingAreaService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.CreateParkingAreaRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.ParkingAreaResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ParkingAreaControllerTest {

    @Mock
    private ParkingAreaService parkingAreaService;

    @InjectMocks
    private ParkingAreaController parkingAreaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(parkingAreaController).build();
    }

    @Test
    public void testCreateParkingArea() throws Exception {
        CreateParkingAreaRequestDto requestDto = new CreateParkingAreaRequestDto();
        requestDto.setLatitude(10.0);
        requestDto.setLongitude(20.0);

        ParkingAreaResponseDto responseDto = new ParkingAreaResponseDto();
        responseDto.setId(1L);
        responseDto.setLatitude(10.0);
        responseDto.setLongitude(20.0);

        when(parkingAreaService.createParkingArea(any(CreateParkingAreaRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/parking-areas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"latitude\": 10.0, \"longitude\": 20.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.latitude").value(10.0))
                .andExpect(jsonPath("$.longitude").value(20.0));

        verify(parkingAreaService, times(1)).createParkingArea(any(CreateParkingAreaRequestDto.class));
    }

    @Test
    public void testGetParkingAreaById() throws Exception {
        ParkingAreaResponseDto responseDto = new ParkingAreaResponseDto();
        responseDto.setId(1L);
        responseDto.setLatitude(10.0);
        responseDto.setLongitude(20.0);

        when(parkingAreaService.getParkingAreaById(anyLong())).thenReturn(responseDto);

        mockMvc.perform(get("/parking-areas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.latitude").value(10.0))
                .andExpect(jsonPath("$.longitude").value(20.0));

        verify(parkingAreaService, times(1)).getParkingAreaById(anyLong());
    }

    @Test
    public void testUpdateParkingArea() throws Exception {
        CreateParkingAreaRequestDto requestDto = new CreateParkingAreaRequestDto();
        requestDto.setLatitude(10.0);
        requestDto.setLongitude(20.0);

        ParkingAreaResponseDto responseDto = new ParkingAreaResponseDto();
        responseDto.setId(1L);
        responseDto.setLatitude(10.0);
        responseDto.setLongitude(20.0);

        when(parkingAreaService.updateParkingArea(anyLong(), any(CreateParkingAreaRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/parking-areas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"latitude\": 10.0, \"longitude\": 20.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.latitude").value(10.0))
                .andExpect(jsonPath("$.longitude").value(20.0));

        verify(parkingAreaService, times(1)).updateParkingArea(anyLong(), any(CreateParkingAreaRequestDto.class));
    }

    @Test
    public void testDeleteParkingArea() throws Exception {
        doNothing().when(parkingAreaService).deleteParkingArea(anyLong());

        mockMvc.perform(delete("/parking-areas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(parkingAreaService, times(1)).deleteParkingArea(anyLong());
    }

    @Test
    public void testGetAllParkingAreas() throws Exception {
        // Arrange
        ParkingAreaResponseDto parkingAreaResponse = new ParkingAreaResponseDto();
        parkingAreaResponse.setId(1L);
        parkingAreaResponse.setLatitude(10.0);
        parkingAreaResponse.setLongitude(20.0);

        List<ParkingAreaResponseDto> parkingAreaResponses = Collections.singletonList(parkingAreaResponse);
        Page<ParkingAreaResponseDto> parkingAreaPage = new PageImpl<>(parkingAreaResponses, PageRequest.of(0, 10), 1);

        when(parkingAreaService.getAllParkingAreas(any(Pageable.class))).thenReturn(parkingAreaPage);

        // Act & Assert
        mockMvc.perform(get("/parking-areas")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].latitude").value(10.0))
                .andExpect(jsonPath("$.content[0].longitude").value(20.0));

        verify(parkingAreaService, times(1)).getAllParkingAreas(any(Pageable.class));
    }
}
