package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils.AuthorizationUtils;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.infrastructure.ScooterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public ScooterService(ScooterRepository scooterRepository,  AuthorizationUtils authorizationUtils) {
        this.scooterRepository = scooterRepository;
        this.modelMapper = new ModelMapper();
        this.authorizationUtils = authorizationUtils;
    }


}
