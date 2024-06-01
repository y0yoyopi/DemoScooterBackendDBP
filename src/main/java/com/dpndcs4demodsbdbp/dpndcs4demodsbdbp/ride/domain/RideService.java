package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils.AuthorizationUtils;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure.RideRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.infrastructure.ScooterRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.infrastructure.TenantRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final TenantRepository tenantRepository;
    private final ScooterRepository scooterRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final AuthorizationUtils authorizationUtils;
    private final UserRepository userRepository;


    @Autowired
    public RideService(RideRepository rideRepository, TenantRepository tenantRepository, ScooterRepository scooterRepository, AuthorizationUtils authorizationUtils, @Qualifier("userRepository") UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.tenantRepository = tenantRepository;
        this.scooterRepository = scooterRepository;
        this.authorizationUtils = authorizationUtils;
        this.userRepository = userRepository;
    }


}
