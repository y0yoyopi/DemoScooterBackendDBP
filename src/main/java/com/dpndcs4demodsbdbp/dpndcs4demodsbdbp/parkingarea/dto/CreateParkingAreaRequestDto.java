package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateParkingAreaRequestDto {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;


}
