package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RideResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private Long scooterId;

    @NotNull
    private Long tenantId;

    @NotNull
    private String status;

    private Long originParkingAreaId;
    private Long destinationParkingAreaId;
    private Double price;
}
