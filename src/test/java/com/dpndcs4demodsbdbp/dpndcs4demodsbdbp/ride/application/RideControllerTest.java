package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.application;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.RideService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.*;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.CreateRideRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RideControllerTest {

    @Mock
    private RideService rideService;

    @InjectMocks
    private RideController rideController;

    private MockMvc mockMvc;

    private Tenant tenant;
    private Scooter scooter;
    private CreateRideRequestDto createRideRequestDto;
    private RideResponseDto rideResponseDto;
    private RideDetailsDto rideDetailsDto;
    private RidesByUserDto ridesByUserDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();

        tenant = new Tenant();
        tenant.setId(1L);
        tenant.setEmail("tenant@example.com");

        scooter = new Scooter();
        scooter.setId(1L);
        scooter.setStatus(ScooterStatus.AVAILABLE);

        createRideRequestDto = new CreateRideRequestDto();
        createRideRequestDto.setScooterId(1L);
        createRideRequestDto.setOriginParkingAreaId(1L);
        createRideRequestDto.setPrice(10.0);

        rideResponseDto = new RideResponseDto();
        rideResponseDto.setStatus("ONGOING");
        rideResponseDto.setPrice(10.0);

        rideDetailsDto = new RideDetailsDto();
        rideDetailsDto.setId(1L);
        rideDetailsDto.setStatus("ONGOING");

        ridesByUserDto = new RidesByUserDto();
        ridesByUserDto.setId(1L);
        ridesByUserDto.setStatus("ONGOING");
    }

    @Test
    public void testStartRide() throws Exception {
        when(rideService.createRide(any(CreateRideRequestDto.class))).thenReturn(rideResponseDto);

        mockMvc.perform(post("/ride/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"scooterId\": 1, \"originParkingAreaId\": 1, \"price\": 10.0 }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testCompleteRide() throws Exception {
        when(rideService.completeRide(anyLong(), anyLong())).thenReturn(rideResponseDto);

        mockMvc.perform(patch("/ride/complete/{rideId}", 1L)
                        .param("destinationParkingAreaId", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testCancelRide() throws Exception {
        mockMvc.perform(patch("/ride/delete/{rideId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRideById() throws Exception {
        when(rideService.getRideById(anyLong())).thenReturn(rideDetailsDto);

        mockMvc.perform(get("/ride/{rideId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testGetRidesByTenant() throws Exception {
        when(rideService.getRidesByTenant(anyLong())).thenReturn(List.of(ridesByUserDto));

        mockMvc.perform(get("/ride/user/{tenantId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}