package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RideRepository extends JpaRepository<Ride, Long> {
    Page<Ride> findAllByTenantIdAndStatus(Long tenant_id, Status status, Pageable pageable);

    List<Ride> findByTenantId(Long tenantId);
}
