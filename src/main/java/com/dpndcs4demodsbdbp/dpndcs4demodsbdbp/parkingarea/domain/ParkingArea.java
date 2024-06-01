package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ParkingArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    //Relación de un ParkingArea a muchos scooters
    @OneToMany(mappedBy = "ParkingArea")
    private List<Scooter> scooters = new ArrayList<>();

    //Constructores

    public ParkingArea() {}

    public ParkingArea(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
