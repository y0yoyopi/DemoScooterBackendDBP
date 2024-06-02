package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponseDto {

    private Long id;
    private Double amount;
    private LocalDateTime transactionDateTime;
    private Long rideId;
    private Long tenantId;
}