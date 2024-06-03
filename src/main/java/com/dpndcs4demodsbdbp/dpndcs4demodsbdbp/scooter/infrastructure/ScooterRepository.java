package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.infrastructure;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ScooterRepository extends JpaRepository<Scooter, Long>, PagingAndSortingRepository<Scooter, Long> {

}


