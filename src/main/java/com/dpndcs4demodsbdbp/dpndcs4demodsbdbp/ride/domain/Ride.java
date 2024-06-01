package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain.Transaction;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String destinationName;

    @Column(nullable = false)
    private Status status;

    @Column
    private LocalDateTime departureDate;

    @Column
    private LocalDateTime arrivalDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Transaction price;

    @OneToOne(cascade = CascadeType.ALL)
    private ParkingArea originParkingArea;

    @OneToOne(cascade = CascadeType.ALL)
    private ParkingArea destinationParkingArea;

    //Relación de muchos viajes a un tenant
    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Tenant tenant;

    //Relación de muchos viajes a un scooter
    @ManyToOne
    @JoinColumn(name = "scooter_id")
    private Scooter scooter;


}
