package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.AbstractIntegrationTest;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Status;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RideRepositoryTest extends AbstractIntegrationTest {

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
        tenant.setEmail("jane.doe@example.com");
        tenant.setPassword("password");
        tenant.setPhoneNumber("0987654321");
        entityManager.persist(tenant);

        scooter = new Scooter();
        scooter.setModel("Model X");
        scooter.setStatus(ScooterStatus.AVAILABLE);
        entityManager.persist(scooter);

        parkingArea = new ParkingArea();
        entityManager.persist(parkingArea);

        setupAndPersistTestRide(Status.ONGOING);
        setupAndPersistTestRide(Status.COMPLETED);
    }

    private void setupAndPersistTestRide(Status status) {
        Ride ride = createTestRide(status);
        entityManager.persist(ride);
    }

    private Ride createTestRide(Status status) {
        Ride ride = new Ride();
        ride.setTenant(tenant);
        ride.setScooter(scooter);
        ride.setStatus(status);
        ride.setDepartureDate(LocalDateTime.now());
        ride.setArrivalDate(LocalDateTime.now().plusHours(1));
        ride.setOriginParkingArea(parkingArea);
        ride.setDestinationParkingArea(parkingArea);
        return ride;
    }

    @Test
    public void testFindAllByTenantIdAndStatus() {
        Page<Ride> ridesPage = rideRepository.findAllByTenantIdAndStatus(tenant.getId(), Status.ONGOING, PageRequest.of(0, 10));

        assertEquals(1, ridesPage.getTotalElements());
        Ride retrievedRide = ridesPage.getContent().get(0);
        assertEquals(Status.ONGOING, retrievedRide.getStatus());
    }

    @Test
    public void testCreateRide() {
        Ride ride = createTestRide(Status.ONGOING);
        Ride savedRide = rideRepository.save(ride);
        Optional<Ride> retrievedRide = rideRepository.findById(savedRide.getId());
        assertTrue(retrievedRide.isPresent());
        assertEquals(ride, retrievedRide.get());
    }

    @Test
    public void testFindById() {
        Ride ride = createTestRide(Status.COMPLETED);
        Ride savedRide = rideRepository.save(ride);
        Optional<Ride> retrievedRide = rideRepository.findById(savedRide.getId());
        assertTrue(retrievedRide.isPresent());
        assertEquals(savedRide, retrievedRide.get());
    }

    @Test
    public void testDeleteById() {
        Ride ride = createTestRide(Status.ONGOING);
        Ride savedRide = rideRepository.save(ride);
        rideRepository.deleteById(savedRide.getId());
        Optional<Ride> deletedRide = rideRepository.findById(savedRide.getId());
        assertFalse(deletedRide.isPresent());
    }

    @Test
    public void testFindAllByArrivalDateAndDestinationParkingArea() {
        Ride ride = createTestRide(Status.ONGOING);
        Ride savedRide = rideRepository.save(ride);
        List<Ride> retrievedRides = rideRepository.findAllByArrivalDateAndDestinationParkingArea(savedRide.getArrivalDate(), savedRide.getDestinationParkingArea());

        assertFalse(retrievedRides.isEmpty());
        assertEquals(1, retrievedRides.size());

        for (Ride retrievedRide : retrievedRides) {
            assertEquals(savedRide.getArrivalDate(), retrievedRide.getArrivalDate());
            assertEquals(savedRide.getDestinationParkingArea(), retrievedRide.getDestinationParkingArea());
        }
    }
}