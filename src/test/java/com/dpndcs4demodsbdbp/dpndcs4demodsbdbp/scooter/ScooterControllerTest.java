package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.application.ScooterController;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto.CreateScooterRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto.ScooterResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ScooterControllerTest {

    @Mock
    private ScooterService scooterService;

    @InjectMocks
    private ScooterController scooterController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(scooterController).build();
    }

    @Test
    public void testCreateScooter() throws Exception {
        CreateScooterRequestDto requestDto = new CreateScooterRequestDto();
        requestDto.setModel("Model A");
        requestDto.setParkingAreaId(1L);
        requestDto.setStatus(ScooterStatus.AVAILABLE);

        ScooterResponseDto responseDto = new ScooterResponseDto();
        responseDto.setId(1L);
        responseDto.setModel("Model A");
        responseDto.setParkingAreaId(1L);
        responseDto.setStatus(ScooterStatus.AVAILABLE);

        when(scooterService.createScooter(any(CreateScooterRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/scooter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"model\": \"Model A\", \"parkingAreaId\": 1, \"status\": \"AVAILABLE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.model").value("Model A"))
                .andExpect(jsonPath("$.parkingAreaId").value(1L))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));

        verify(scooterService, times(1)).createScooter(any(CreateScooterRequestDto.class));
    }

    @Test
    public void testGetScooterById() throws Exception {
        ScooterResponseDto responseDto = new ScooterResponseDto();
        responseDto.setId(1L);
        responseDto.setModel("Model A");
        responseDto.setParkingAreaId(1L);
        responseDto.setStatus(ScooterStatus.AVAILABLE);

        when(scooterService.getScooterById(anyLong())).thenReturn(responseDto);

        mockMvc.perform(get("/scooter/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.model").value("Model A"))
                .andExpect(jsonPath("$.parkingAreaId").value(1L))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));

        verify(scooterService, times(1)).getScooterById(anyLong());
    }

    @Test
    public void testGetAllScooters() throws Exception {
        ScooterResponseDto responseDto = new ScooterResponseDto();
        responseDto.setId(1L);
        responseDto.setModel("Model A");
        responseDto.setParkingAreaId(1L);
        responseDto.setStatus(ScooterStatus.AVAILABLE);

        Page<ScooterResponseDto> page = new PageImpl<>(Collections.singletonList(responseDto), PageRequest.of(0, 10), 1);

        when(scooterService.getAllScooters(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/scooter")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].model").value("Model A"))
                .andExpect(jsonPath("$.content[0].parkingAreaId").value(1L))
                .andExpect(jsonPath("$.content[0].status").value("AVAILABLE"));

        verify(scooterService, times(1)).getAllScooters(any(Pageable.class));
    }

    @Test
    public void testUpdateScooter() throws Exception {
        CreateScooterRequestDto requestDto = new CreateScooterRequestDto();
        requestDto.setModel("Model B");
        requestDto.setParkingAreaId(1L);
        requestDto.setStatus(ScooterStatus.IN_USE);

        ScooterResponseDto responseDto = new ScooterResponseDto();
        responseDto.setId(1L);
        responseDto.setModel("Model B");
        responseDto.setParkingAreaId(1L);
        responseDto.setStatus(ScooterStatus.IN_USE);

        when(scooterService.updateScooter(anyLong(), any(CreateScooterRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/scooter/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"model\": \"Model B\", \"parkingAreaId\": 1, \"status\": \"IN_USE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.model").value("Model B"))
                .andExpect(jsonPath("$.parkingAreaId").value(1L))
                .andExpect(jsonPath("$.status").value("IN_USE"));

        verify(scooterService, times(1)).updateScooter(anyLong(), any(CreateScooterRequestDto.class));
    }

    @Test
    public void testDeleteScooter() throws Exception {
        doNothing().when(scooterService).deleteScooter(anyLong());

        mockMvc.perform(delete("/scooter/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(scooterService, times(1)).deleteScooter(anyLong());
    }

    @Test
    public void testUpdateScooterStatus() throws Exception {
        ScooterResponseDto responseDto = new ScooterResponseDto();
        responseDto.setId(1L);
        responseDto.setModel("Model A");
        responseDto.setParkingAreaId(1L);
        responseDto.setStatus(ScooterStatus.IN_USE);

        when(scooterService.updateScooterStatus(anyLong(), any(ScooterStatus.class))).thenReturn(responseDto);

        mockMvc.perform(patch("/scooter/1/status")
                        .param("status", "IN_USE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.model").value("Model A"))
                .andExpect(jsonPath("$.parkingAreaId").value(1L))
                .andExpect(jsonPath("$.status").value("IN_USE"));

        verify(scooterService, times(1)).updateScooterStatus(anyLong(), any(ScooterStatus.class));
    }
}
