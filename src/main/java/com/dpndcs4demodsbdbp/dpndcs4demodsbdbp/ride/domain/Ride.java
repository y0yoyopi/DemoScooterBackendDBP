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

    // Relación de muchos viajes a un tenant
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    // Relación de muchos viajes a un scooter
    @ManyToOne
    @JoinColumn(name = "scooter_id")
    private Scooter scooter;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Transaction getPrice() {
        return price;
    }

    public void setPrice(Transaction price) {
        this.price = price;
    }

    public ParkingArea getOriginParkingArea() {
        return originParkingArea;
    }

    public void setOriginParkingArea(ParkingArea originParkingArea) {
        this.originParkingArea = originParkingArea;
    }

    public ParkingArea getDestinationParkingArea() {
        return destinationParkingArea;
    }

    public void setDestinationParkingArea(ParkingArea destinationParkingArea) {
        this.destinationParkingArea = destinationParkingArea;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
    }
}
