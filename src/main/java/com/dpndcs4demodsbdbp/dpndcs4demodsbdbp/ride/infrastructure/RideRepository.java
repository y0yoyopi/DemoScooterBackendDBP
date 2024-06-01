package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RideRepository extends JpaRepository<Ride, Long> {
    Page<Ride> findAllByTenantIdAndStatus(Long passenger_id, Status status, Pageable pageable);
}
