package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto;


import lombok.Data;

@Data
public class RideDetailsDto {
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
