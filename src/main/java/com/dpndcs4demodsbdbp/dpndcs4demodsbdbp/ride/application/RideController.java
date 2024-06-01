package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ride")
public class RideController {
    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }
/*
    @PostMapping("/pending")
    public ResponseEntity<BasicRideResponseDto> passengerBookRide(@RequestBody CreateRideRequestDto rideRequest) {
        BasicRideResponseDto response = rideService.createRide(rideRequest);
        return ResponseEntity.ok(response);
    }*/
}
