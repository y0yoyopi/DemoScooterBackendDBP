package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.RideService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.CreateRideRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideDetailsDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RidesByUserDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.domain.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{rideId}")
    public ResponseEntity<RideDetailsDto> getRideById(@PathVariable Long rideId) {
        RideDetailsDto response = rideService.getRideById(rideId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{tenantId}")
    public ResponseEntity<List<RidesByUserDto>> getRidesByTenant(@PathVariable Long tenantId) {
        List<RidesByUserDto> response = rideService.getRidesByTenant(tenantId);
        return ResponseEntity.ok(response);
    }


}