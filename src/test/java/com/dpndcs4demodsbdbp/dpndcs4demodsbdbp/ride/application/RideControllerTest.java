package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.RideService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.CreateRideRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideDetailsDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RidesByUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RideControllerTest {

    @InjectMocks
    private RideController rideController;

    @Mock
    private RideService rideService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
    }

    @Test
    public void testStartRide() throws Exception {
        CreateRideRequestDto requestDto = new CreateRideRequestDto();
        requestDto.setScooterId(1L);
        requestDto.setOriginParkingAreaId(1L);
        requestDto.setPrice(10.0);
        requestDto.setEmail("test@tenant.com");

        RideResponseDto responseDto = new RideResponseDto();
        responseDto.setStatus("ONGOING");
        responseDto.setPrice(10.0);

        when(rideService.createRide(any(CreateRideRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/ride/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"scooterId\":1,\"originParkingAreaId\":1,\"price\":10.0,\"email\":\"test@tenant.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("ONGOING")))
                .andExpect(jsonPath("$.price", is(10.0)));
    }

    @Test
    public void testCompleteRide() throws Exception {
        RideResponseDto responseDto = new RideResponseDto();
        responseDto.setStatus("COMPLETED");
        responseDto.setPrice(10.0);

        when(rideService.completeRide(anyLong(), anyLong())).thenReturn(responseDto);

        mockMvc.perform(patch("/ride/complete/1")
                        .param("destinationParkingAreaId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("COMPLETED")))
                .andExpect(jsonPath("$.price", is(10.0)));
    }

    @Test
    public void testCancelRide() throws Exception {
        mockMvc.perform(patch("/ride/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetRideById() throws Exception {
        RideDetailsDto responseDto = new RideDetailsDto();
        responseDto.setId(1L);
        responseDto.setStatus("ONGOING");

        when(rideService.getRideById(anyLong())).thenReturn(responseDto);

        mockMvc.perform(get("/ride/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("ONGOING")));
    }

    @Test
    public void testGetRidesByTenant() throws Exception {
        RidesByUserDto ridesByUserDto = new RidesByUserDto();
        ridesByUserDto.setId(1L);
        ridesByUserDto.setStatus("COMPLETED");

        List<RidesByUserDto> responseDto = Collections.singletonList(ridesByUserDto);

        when(rideService.getRidesByTenant(anyLong())).thenReturn(responseDto);

        mockMvc.perform(get("/ride/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("COMPLETED")));
    }
}
