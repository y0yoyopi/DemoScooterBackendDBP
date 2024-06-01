package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double money;

    @Column(nullable = false)
    private LocalDateTime transaction_time;
}
