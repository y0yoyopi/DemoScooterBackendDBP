package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relación de un scooter a varios rides
    @OneToMany(mappedBy = "scooter")
    private List<Ride> rides;
}
