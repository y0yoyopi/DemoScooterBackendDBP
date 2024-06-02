package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.RideService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.CreateRideRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ride")
public class RideController {
    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/start")
    public ResponseEntity<RideResponseDto> startRide(@RequestBody CreateRideRequestDto rideRequest) {
        RideResponseDto response = rideService.createRide(rideRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/complete/{rideId}")
    public ResponseEntity<RideResponseDto> completeRide(@PathVariable Long rideId, @RequestParam Long destinationParkingAreaId) {
        RideResponseDto response = rideService.completeRide(rideId, destinationParkingAreaId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/delete/{rideId}")
    public ResponseEntity<String> cancelRide(@PathVariable Long rideId) {
        rideService.cancelRide(rideId);
        return ResponseEntity.ok("Ride cancelled");
    }
}