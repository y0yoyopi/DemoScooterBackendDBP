package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scooter")
public class ScooterController {

    private final ScooterService scooterService;

    @Autowired
    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDto> getDriver(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverInfo(id));
    }*/
}
