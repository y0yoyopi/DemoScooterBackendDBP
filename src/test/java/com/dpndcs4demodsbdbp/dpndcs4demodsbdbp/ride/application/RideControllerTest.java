package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.application;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.RideService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Status;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.*;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure.RideRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.infrastructure.ScooterRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.infrastructure.TenantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RideControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ScooterRepository scooterRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Tenant tenant;
    private Scooter scooter;
    private Ride ride;

    @Autowired
    private RideService rideService;

    @BeforeEach
    public void setUp() {
        tenant = new Tenant();
        tenant.setFirstName("err");
        tenant.setLastName("wutt");
        tenant.setEmail("err.wut@example.com");
        tenantRepository.save(tenant);

        scooter = new Scooter();
        scooter.setStatus(ScooterStatus.AVAILABLE);
        scooterRepository.save(scooter);

        ride = new Ride();
        ride.setTenant(tenant);
        ride.setScooter(scooter);
        ride.setDepartureDate(LocalDateTime.now());
        ride.setArrivalDate(LocalDateTime.now().plusHours(1));
        ride.setStatus(Status.ONGOING);
        ride.setOriginParkingArea(new ParkingArea(40.7128, -74.0060));
    }

    @Test
    public void testStartRide() throws Exception {
        CreateRideRequestDto requestDto = new CreateRideRequestDto();
        requestDto.setEmail("err.wut@example.com");

        RideResponseDto responseDto = new RideResponseDto();
        responseDto.setId(1L);

        when(rideService.createRide(any(CreateRideRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/ride/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(rideService, times(1)).createRide(any(CreateRideRequestDto.class));
    }

    @Test
    public void testCompleteRide() throws Exception {
        Long rideId = 1L;
        Long destinationParkingAreaId = 1L;

        RideResponseDto responseDto = new RideResponseDto();
        responseDto.setId(rideId);

        when(rideService.completeRide(rideId, destinationParkingAreaId)).thenReturn(responseDto);

        mockMvc.perform(patch("/ride/complete/{rideId}", rideId)
                        .param("destinationParkingAreaId", destinationParkingAreaId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(rideService, times(1)).completeRide(rideId, destinationParkingAreaId);
    }

    @Test
    public void testCancelRide() throws Exception {
        Long rideId = 1L;

        doNothing().when(rideService).cancelRide(rideId);

        mockMvc.perform(delete("/ride/delete/{rideId}", rideId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(rideService, times(1)).cancelRide(rideId);
    }

    @Test
    public void testGetRideById() throws Exception {
        Long rideId = 1L;

        RideDetailsDto responseDto = new RideDetailsDto();
        responseDto.setId(rideId);

        when(rideService.getRideById(rideId)).thenReturn(responseDto);

        mockMvc.perform(get("/ride/{rideId}", rideId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(rideService, times(1)).getRideById(rideId);
    }

    @Test
    public void testGetRidesByTenant() throws Exception {
        Long tenantId = 1L;

        RidesByUserDto responseDto = new RidesByUserDto();
        responseDto.setTenantId(tenantId);

        when(rideService.getRidesByTenant(tenantId)).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/ride/user/{tenantId}", tenantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(rideService, times(1)).getRidesByTenant(tenantId);
    }
}