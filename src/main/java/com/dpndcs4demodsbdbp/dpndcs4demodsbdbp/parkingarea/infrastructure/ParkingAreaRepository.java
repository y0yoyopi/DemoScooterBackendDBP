package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.infrastructure;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingAreaRepository extends CrudRepository<ParkingArea, Long>, PagingAndSortingRepository<ParkingArea, Long> {
    Optional<ParkingArea> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
