package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.dto.JwtAuthResponse;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.dto.LoginReq;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.dto.RegisterReq;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.exceptions.UserAlreadyExistException;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.config.JwtService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.domain.Staff;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain.User;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository<User> userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthService(UserRepository<User> userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    public JwtAuthResponse login(LoginReq req) {
        Optional<User> user;
        user = userRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }

    public JwtAuthResponse register(RegisterReq req){
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistException("Email is already registered");

        if (req.getIsStaff()) {
            Staff staff = new Staff();/*
            driver.setCategory(req.getCategory());
            driver.setVehicle(modelMapper.map(req.getVehicle(), Vehicle.class));
            driver.setTrips(0);
            driver.setAvgRating(0f);
            driver.setCreatedAt(ZonedDateTime.now());
            driver.setRole(Role.DRIVER);
            driver.setFirstName(req.getFirstName());
            driver.setLastName(req.getLastName());
            driver.setEmail(req.getEmail());
            driver.setPassword(passwordEncoder.encode(req.getPassword()));
            driver.setPhoneNumber(req.getPhone());*/

            userRepository.save(staff);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(staff));
            return response;
        }
        else {
            Tenant tenant = new Tenant();/*
            tenant.setCreatedAt(ZonedDateTime.now());
            tenant.setRole(Role.TENANT);
            tenant.setFirstName(req.getFirstName());
            tenant.setLastName(req.getLastName());
            tenant.setEmail(req.getEmail());
            tenant.setPassword(passwordEncoder.encode(req.getPassword()));
            tenant.setPhoneNumber(req.getPhone());
            tenant.setAvgRating(0f);
            tenant.setTrips(0);*/

            userRepository.save(tenant);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(tenant));
            return response;
        }

    }
}