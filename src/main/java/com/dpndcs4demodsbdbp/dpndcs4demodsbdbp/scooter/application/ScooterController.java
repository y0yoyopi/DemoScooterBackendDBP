package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto.CreateScooterRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto.ScooterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scooter")
public class ScooterController {

    private final ScooterService scooterService;

    @Autowired
    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @PostMapping
    public ResponseEntity<ScooterResponseDto> createScooter(@RequestBody CreateScooterRequestDto scooterRequest) {
        ScooterResponseDto response = scooterService.createScooter(scooterRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{scooterId}")
    public ResponseEntity<ScooterResponseDto> getScooterById(@PathVariable Long scooterId) {
        ScooterResponseDto response = scooterService.getScooterById(scooterId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ScooterResponseDto>> getAllScooters() {
        List<ScooterResponseDto> response = scooterService.getAllScooters();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{scooterId}")
    public ResponseEntity<ScooterResponseDto> updateScooter(@PathVariable Long scooterId, @RequestBody CreateScooterRequestDto scooterRequest) {
        ScooterResponseDto response = scooterService.updateScooter(scooterId, scooterRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{scooterId}")
    public ResponseEntity<String> deleteScooter(@PathVariable Long scooterId) {
        scooterService.deleteScooter(scooterId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{scooterId}/status")
    public ResponseEntity<ScooterResponseDto> updateScooterStatus(@PathVariable Long scooterId, @RequestParam ScooterStatus status) {
        ScooterResponseDto response = scooterService.updateScooterStatus(scooterId, status);
        return ResponseEntity.ok(response);
    }

}
