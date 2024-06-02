package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils.AuthorizationUtils;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.infrastructure.ParkingAreaRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.CreateRideRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure.RideRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.infrastructure.ScooterRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.infrastructure.StaffRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.exceptions.UnauthorizeOperationException;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.infrastructure.TenantRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain.Transaction;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final TenantRepository tenantRepository;
    private final ScooterRepository scooterRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final AuthorizationUtils authorizationUtils;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final ParkingAreaRepository parkingAreaRepository;


    @Autowired
    public RideService(RideRepository rideRepository, TenantRepository tenantRepository, StaffRepository staffRepository, ScooterRepository scooterRepository, AuthorizationUtils authorizationUtils, ParkingAreaRepository parkingAreaRepository,@Qualifier("userRepository") UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.tenantRepository = tenantRepository;
        this.staffRepository = staffRepository;
        this.scooterRepository = scooterRepository;
        this.authorizationUtils = authorizationUtils;
        this.parkingAreaRepository = parkingAreaRepository;
        this.userRepository = userRepository;
    }

    public RideResponseDto createRide(CreateRideRequestDto rideRequest) {

        String userEmail = authorizationUtils.getCurrentUserEmail();

        Tenant tenant = tenantRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Tenant not found"));

        Scooter scooter = scooterRepository.findById(rideRequest.getScooterId())
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));

        ParkingArea originParkingArea = parkingAreaRepository.findById(rideRequest.getOriginParkingAreaId())
                .orElseThrow(() -> new IllegalArgumentException("Parking Area not found"));

        Ride ride = new Ride();
        ride.setTenant(tenant);
        ride.setScooter(scooter);
        ride.setStatus(Status.ONGOING);
        ride.setDepartureDate(LocalDateTime.now());
        ride.setOriginParkingArea(originParkingArea);

        Transaction transaction = new Transaction();
        transaction.setAmount(rideRequest.getPrice());
        transaction.setRide(ride);
        transaction.setTransactionDateTime(LocalDateTime.now());

        ride.setPrice(transaction);

        rideRepository.save(ride);

        RideResponseDto response = modelMapper.map(ride, RideResponseDto.class);
        response.setStatus(Status.ONGOING.name());
        response.setPrice(rideRequest.getPrice());

        return response;
    }

    //
    public RideResponseDto completeRide(Long rideId, Long destinationParkingAreaId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        ParkingArea destinationParkingArea = parkingAreaRepository.findById(destinationParkingAreaId)
                .orElseThrow(() -> new IllegalArgumentException("Parking Area not found"));

        ride.setStatus(Status.COMPLETED);
        ride.setArrivalDate(LocalDateTime.now());
        ride.setDestinationParkingArea(destinationParkingArea);

        double price = ride.getPrice().getAmount();

        ride.getPrice().setAmount(price);
        rideRepository.save(ride);

        RideResponseDto response = modelMapper.map(ride, RideResponseDto.class);
        response.setStatus(Status.COMPLETED.name());
        response.setPrice(price);

        return response;
    }

    public void cancelRide(Long rideId) {
        String email = authorizationUtils.getCurrentUserEmail();
        if(tenantRepository.findByEmail(email).isEmpty() && staffRepository.findByEmail(email).isEmpty()){
            throw new UnauthorizeOperationException("You don't have authorization to access this resource");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        if (ride.getStatus() != Status.ONGOING) {
            throw new IllegalArgumentException("Ride can only be cancelled if it is ongoing");
        }

        ride.setStatus(Status.CANCELLED);
        rideRepository.save(ride);
    }

    }



