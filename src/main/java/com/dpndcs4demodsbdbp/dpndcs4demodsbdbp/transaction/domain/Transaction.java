package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain;

import jakarta.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double money;
}
