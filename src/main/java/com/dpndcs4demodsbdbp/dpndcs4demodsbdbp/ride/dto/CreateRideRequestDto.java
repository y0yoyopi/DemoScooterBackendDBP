package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateRideRequestDto {
    @NotNull
    private Long scooterId;

    @NotNull
    private Long tenantId;

    @NotNull
    private Long originParkingAreaId;

    @NotNull
    @Positive
    private Double price;
}
