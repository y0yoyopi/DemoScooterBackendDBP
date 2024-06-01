package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.infrastructure;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingAreaRepository extends JpaRepository<ParkingArea, Long> {
    Optional<ParkingArea> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
