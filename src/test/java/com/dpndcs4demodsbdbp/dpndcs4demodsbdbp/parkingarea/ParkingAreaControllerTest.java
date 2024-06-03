package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingAreaService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.CreateParkingAreaRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.ParkingAreaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ParkingAreaControllerTest {

    @Mock
    private ParkingAreaService parkingAreaService;

    @InjectMocks
    private ParkingAreaController parkingAreaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateParkingArea() {
        CreateParkingAreaRequestDto requestDto = new CreateParkingAreaRequestDto();
        requestDto.setLatitude(40.7128);
        requestDto.setLongitude(-74.0060);

        ParkingAreaResponseDto responseDto = new ParkingAreaResponseDto();
        responseDto.setId(1L);
        responseDto.setLatitude(40.7128);
        responseDto.setLongitude(-74.0060);

        when(parkingAreaService.createParkingArea(any(CreateParkingAreaRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<ParkingAreaResponseDto> response = parkingAreaController.createParkingArea(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());

        verify(parkingAreaService, times(1)).createParkingArea(any(CreateParkingAreaRequestDto.class));
    }

    @Test
    void testGetParkingAreaById() {
        ParkingAreaResponseDto responseDto = new ParkingAreaResponseDto();
        responseDto.setId(1L);
        responseDto.setLatitude(40.7128);
        responseDto.setLongitude(-74.0060);

        when(parkingAreaService.getParkingAreaById(1L)).thenReturn(responseDto);

        ResponseEntity<ParkingAreaResponseDto> response = parkingAreaController.getParkingAreaById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());

        verify(parkingAreaService, times(1)).getParkingAreaById(1L);
    }

    @Test
    void testGetAllParkingAreas() {
        ParkingAreaResponseDto responseDto1 = new ParkingAreaResponseDto();
        responseDto1.setId(1L);
        responseDto1.setLatitude(40.7128);
        responseDto1.setLongitude(-74.0060);

        ParkingAreaResponseDto responseDto2 = new ParkingAreaResponseDto();
        responseDto2.setId(2L);
        responseDto2.setLatitude(34.0522);
        responseDto2.setLongitude(-118.2437);

        Page<ParkingAreaResponseDto> page = new PageImpl<>(Arrays.asList(responseDto1, responseDto2), PageRequest.of(0, 10), 2);

        when(parkingAreaService.getAllParkingAreas(any(Pageable.class))).thenReturn(page);

        ResponseEntity<Page<ParkingAreaResponseDto>> response = parkingAreaController.getAllParkingAreas(PageRequest.of(0, 10));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getTotalElements());

        verify(parkingAreaService, times(1)).getAllParkingAreas(any(Pageable.class));
    }
}
