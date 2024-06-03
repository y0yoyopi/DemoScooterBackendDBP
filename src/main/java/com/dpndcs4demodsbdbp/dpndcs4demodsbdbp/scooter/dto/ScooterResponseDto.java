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

    public ScooterResponseDto() {
    }

    public ScooterResponseDto(Long id, String model, Long parkingAreaId, ScooterStatus status) {
        this.id = id;
        this.model = model;
        this.parkingAreaId = parkingAreaId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getParkingAreaId() {
        return parkingAreaId;
    }

    public void setParkingAreaId(Long parkingAreaId) {
        this.parkingAreaId = parkingAreaId;
    }

    public ScooterStatus getStatus() {
        return status;
    }

    public void setStatus(ScooterStatus status) {
        this.status = status;
    }
}
