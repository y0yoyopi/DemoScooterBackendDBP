package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CreateScooterRequestDto {
    @NotNull
    private String model;
    @NotNull
    private Long parkingAreaId;
    @NotNull
    private ScooterStatus status;

    public CreateScooterRequestDto() {
    }

    public CreateScooterRequestDto(@NotNull String model, @NotNull Long parkingAreaId, @NotNull ScooterStatus status) {
        this.model = model;
        this.parkingAreaId = parkingAreaId;
        this.status = status;
    }


}