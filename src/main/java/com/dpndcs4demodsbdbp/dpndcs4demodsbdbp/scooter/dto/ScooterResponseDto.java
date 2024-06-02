package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScooterResponseDto {
    @NotNull
    private Long id;
    private String model;
    @NotNull
    private Long parkingAreaId;
    @NotNull
    private ScooterStatus status;
}
