package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTransactionRequestDto {
    @NotNull
    private Double amount;
    @NotNull
    private Long rideId;
    @NotNull
    private Long tenantId;
}