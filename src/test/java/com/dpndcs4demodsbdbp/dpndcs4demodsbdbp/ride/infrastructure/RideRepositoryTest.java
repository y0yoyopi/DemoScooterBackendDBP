package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Status;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RideRepositoryTest {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Tenant tenant;
    private Scooter scooter;
    private ParkingArea parkingArea;

    @BeforeEach
    public void setUp() {
        tenant = new Tenant();
        tenant.setFirstName("Jane");
        tenant.setLastName("Doe");
        tenant.setEmail("jane@example.com");
        entityManager.persist(tenant);

        scooter = new Scooter();
        scooter.setStatus(ScooterStatus.AVAILABLE);
        entityManager.persist(scooter);

        parkingArea = new ParkingArea(40.7128, -74.0060);
        entityManager.persist(parkingArea);
    }

    @Test
    public void testCreateRide() {
        Ride ride = createTestRide(Status.ONGOING);
        Ride savedRide = rideRepository.save(ride);
        Optional<Ride> retrievedRide = rideRepository.findById(savedRide.getId());
        assertThat(retrievedRide).isPresent();
        assertThat(retrievedRide.get()).isEqualTo(savedRide);
    }

    @Test
    public void testFindById() {
        Ride ride = createTestRide(Status.ONGOING);
        Ride savedRide = rideRepository.save(ride);
        Ride foundRide = entityManager.find(Ride.class, ride.getId());
        assertThat(foundRide).isNotNull();
        assertThat(savedRide).isEqualTo(foundRide);
    }

    @Test
    public void testDeleteById() {
        Ride ride = createTestRide(Status.ONGOING);
        Ride savedRide = rideRepository.save(ride);
        rideRepository.deleteById(savedRide.getId());
        Ride deletedRide = entityManager.find(Ride.class, savedRide.getId());
        assertThat(deletedRide).isNull();
    }

    private Ride createTestRide(Status status) {
        Ride ride = new Ride();
        ride.setTenant(tenant);
        ride.setScooter(scooter);
        ride.setStatus(status);
        ride.setDepartureDate(LocalDateTime.now());
        ride.setOriginParkingArea(parkingArea);
        return ride;
    }
}
