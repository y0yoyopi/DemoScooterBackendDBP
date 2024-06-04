package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.domain.StaffService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.dto.CreateStaffRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.dto.StaffResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping
    public ResponseEntity<StaffResponseDto> createStaff(@RequestBody CreateStaffRequestDto staffRequest) {
        StaffResponseDto response = staffService.createStaff(staffRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{staffId}")
    public ResponseEntity<StaffResponseDto> getStaffById(@PathVariable Long staffId) {
        StaffResponseDto response = staffService.getStaffById(staffId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{staffId}")
    public ResponseEntity<StaffResponseDto> updateStaff(@PathVariable Long staffId, @RequestBody CreateStaffRequestDto staffRequest) {
        StaffResponseDto response = staffService.updateStaff(staffId, staffRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{staffId}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long staffId) {
        staffService.deleteStaff(staffId);
        return ResponseEntity.noContent().build();
    }
}
