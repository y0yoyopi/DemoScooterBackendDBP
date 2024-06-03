package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Status;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByScooter(Scooter scooter);
    List<Ride> findAllByTenantId(Long tenantId);
    Page<Ride> findAllByTenantIdAndStatus(Long tenantId, Status status, Pageable pageable);

    List<Ride> findByTenantId(Long tenantId);
    default List<Ride> findAllByArrivalDateAndDestinationParkingArea(LocalDateTime arrivalDate, ParkingArea destinationParkingArea) {
        List<Ride> rides = new ArrayList<>();
        List<Ride> allRides = findAll();
        for (Ride ride : allRides) {
            if (ride.getArrivalDate().equals(arrivalDate) && ride.getDestinationParkingArea().equals(destinationParkingArea)) {
                rides.add(ride);
            }
        }
        return rides;
    }
}
