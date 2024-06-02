package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingAreaService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.ParkingAreaResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.CreateParkingAreaRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-areas")
public class ParkingAreaController {

    private final ParkingAreaService parkingAreaService;

    @Autowired
    public ParkingAreaController(ParkingAreaService parkingAreaService) {
        this.parkingAreaService = parkingAreaService;
    }

    @PostMapping
    public ResponseEntity<ParkingAreaResponseDto> createParkingArea(@RequestBody CreateParkingAreaRequestDto parkingAreaRequest) {
        ParkingAreaResponseDto response = parkingAreaService.createParkingArea(parkingAreaRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{parkingAreaId}")
    public ResponseEntity<ParkingAreaResponseDto> getParkingAreaById(@PathVariable Long parkingAreaId) {
        ParkingAreaResponseDto response = parkingAreaService.getParkingAreaById(parkingAreaId);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{parkingAreaId}")
    public ResponseEntity<ParkingAreaResponseDto> updateParkingArea(@PathVariable Long parkingAreaId, @RequestBody CreateParkingAreaRequestDto parkingAreaRequest) {
        ParkingAreaResponseDto response = parkingAreaService.updateParkingArea(parkingAreaId, parkingAreaRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{parkingAreaId}")
    public ResponseEntity<Void> deleteParkingArea(@PathVariable Long parkingAreaId) {
        parkingAreaService.deleteParkingArea(parkingAreaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ParkingAreaResponseDto>> getAllParkingAreas() {
        List<ParkingAreaResponseDto> response = parkingAreaService.getAllParkingAreas();
        return ResponseEntity.ok(response);
    }

}
