package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils.AuthorizationUtils;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.infrastructure.ParkingAreaRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.CreateRideRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideDetailsDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RideResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.dto.RidesByUserDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.events.HelloEmailEvent;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure.RideRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.Scooter;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain.ScooterStatus;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.infrastructure.ScooterRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.domain.Staff;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.infrastructure.StaffRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.exceptions.UnauthorizeOperationException;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.infrastructure.TenantRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain.Transaction;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.infrastructure.TransactionRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.infrastructure.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final TenantRepository tenantRepository;
    private final ScooterRepository scooterRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final AuthorizationUtils authorizationUtils;
    private final TransactionRepository transactionRepository;
    private final StaffRepository staffRepository;
    private final ParkingAreaRepository parkingAreaRepository;
    private final ApplicationEventPublisher applicationEventPublisher;



    @Autowired
    public RideService(RideRepository rideRepository, ApplicationEventPublisher applicationEventPublisher, TransactionRepository transactionRepository, TenantRepository tenantRepository, StaffRepository staffRepository, ScooterRepository scooterRepository, AuthorizationUtils authorizationUtils, ParkingAreaRepository parkingAreaRepository,@Qualifier("userRepository") UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.tenantRepository = tenantRepository;
        this.staffRepository = staffRepository;
        this.scooterRepository = scooterRepository;
        this.authorizationUtils = authorizationUtils;
        this.parkingAreaRepository = parkingAreaRepository;
        this.transactionRepository = transactionRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public RideResponseDto createRide(CreateRideRequestDto rideRequest) {

        String userEmail = authorizationUtils.getCurrentUserEmail();

        Tenant tenant = tenantRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Tenant not found"));

        Scooter scooter = scooterRepository.findById(rideRequest.getScooterId())
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        if (scooter.getStatus() != ScooterStatus.AVAILABLE) {
            throw new IllegalArgumentException("Scooter is not available");
        }

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

        scooter.setStatus(ScooterStatus.IN_USE);
        scooterRepository.save(scooter);

        rideRepository.save(ride);

        RideResponseDto response = modelMapper.map(ride, RideResponseDto.class);
        response.setStatus(Status.ONGOING.name());
        response.setPrice(rideRequest.getPrice());

        applicationEventPublisher.publishEvent(new HelloEmailEvent(this, rideRequest.getEmail()));


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

        Scooter scooter = ride.getScooter();
        scooter.setStatus(ScooterStatus.AVAILABLE);
        scooterRepository.save(scooter);



        Transaction transaction = new Transaction();
        transaction.setAmount(ride.getPrice().getAmount());
        transaction.setRide(ride);
        transaction.setTenant(ride.getTenant());
        transaction.setTransactionDateTime(LocalDateTime.now());

        transactionRepository.save(transaction);

        ride.setPrice(transaction);
        rideRepository.save(ride);

        RideResponseDto response = modelMapper.map(ride, RideResponseDto.class);
        response.setStatus(Status.COMPLETED.name());
        response.setPrice(transaction.getAmount());

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

        Scooter scooter = ride.getScooter();
        scooter.setStatus(ScooterStatus.AVAILABLE);
        scooterRepository.save(scooter);

        ride.setStatus(Status.CANCELLED);
        rideRepository.save(ride);
    }

    public RideDetailsDto getRideById(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        return modelMapper.map(ride, RideDetailsDto.class);
    }

    public List<RidesByUserDto> getRidesByTenant(Long tenantId) {
        String userEmail = authorizationUtils.getCurrentUserEmail();
        if(tenantRepository.findByEmail(userEmail).isEmpty()){
            throw new UnauthorizeOperationException("You aren't a Tenant");
        }
        Tenant tenant = tenantRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Tenant not found"));

        List<Ride> rides = rideRepository.findByTenantId(tenantId);
        return rides.stream()
                .map(ride -> modelMapper.map(ride, RidesByUserDto.class))
                .collect(Collectors.toList());
    }



    }



