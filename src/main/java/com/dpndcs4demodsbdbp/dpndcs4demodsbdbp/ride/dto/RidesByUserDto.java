package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RidesByUserDto {
    private Long id;
    private String status;
    private Long scooterId;
    private Long tenantId;
    private Long originParkingAreaId;
    private Long destinationParkingAreaId;
    private Double price;
    private String departureDate;
    private String arrivalDate;
}
