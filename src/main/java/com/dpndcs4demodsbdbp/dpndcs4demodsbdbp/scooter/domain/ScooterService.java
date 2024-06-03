package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils.AuthorizationUtils;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.infrastructure.ParkingAreaRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto.CreateScooterRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.dto.ScooterResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.scooter.infrastructure.ScooterRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.exceptions.UnauthorizeOperationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;
    private final ParkingAreaRepository parkingAreaRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public ScooterService(ScooterRepository scooterRepository, AuthorizationUtils authorizationUtils, ParkingAreaRepository parkingAreaRepository) {
        this.scooterRepository = scooterRepository;
        this.parkingAreaRepository = parkingAreaRepository;
        this.modelMapper = new ModelMapper();
        this.authorizationUtils = authorizationUtils;
    }

    public ScooterResponseDto createScooter(CreateScooterRequestDto scooterRequest) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to create a scooter");
        }

        ParkingArea parkingArea = parkingAreaRepository.findById(scooterRequest.getParkingAreaId())
                .orElseThrow(() -> new IllegalArgumentException("Parking area not found"));

        Scooter scooter = new Scooter();
        scooter.setModel(scooterRequest.getModel());
        scooter.setParkingArea(parkingArea);
        scooter.setStatus(scooterRequest.getStatus());

        scooter = scooterRepository.save(scooter);
        return modelMapper.map(scooter, ScooterResponseDto.class);
    }

    public ScooterResponseDto getScooterById(Long scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        return modelMapper.map(scooter, ScooterResponseDto.class);
    }

    public Page<ScooterResponseDto> getAllScooters(Pageable pageable) {
        return scooterRepository.findAll(pageable)
                .map(scooter -> modelMapper.map(scooter, ScooterResponseDto.class));
    }

    public ScooterResponseDto updateScooter(Long scooterId, CreateScooterRequestDto scooterRequest) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to update this scooter");
        }

        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));

        ParkingArea parkingArea = parkingAreaRepository.findById(scooterRequest.getParkingAreaId())
                .orElseThrow(() -> new IllegalArgumentException("Parking area not found"));

        scooter.setModel(scooterRequest.getModel());
        scooter.setParkingArea(parkingArea);
        scooter.setStatus(scooterRequest.getStatus());

        scooter = scooterRepository.save(scooter);
        return modelMapper.map(scooter, ScooterResponseDto.class);
    }

    public void deleteScooter(Long scooterId) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to delete this scooter");
        }

        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));

        scooterRepository.delete(scooter);
    }

    public ScooterResponseDto updateScooterStatus(Long scooterId, ScooterStatus status) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to update the status of this scooter");
        }

        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));

        if (status == ScooterStatus.IN_USE && scooter.getStatus() != ScooterStatus.AVAILABLE) {
            throw new IllegalArgumentException("Scooter must be available to set it in use");
        }
        if (status == ScooterStatus.AVAILABLE && scooter.getStatus() == ScooterStatus.IN_USE) {
            throw new IllegalArgumentException("Scooter cannot be set to available while in use");
        }

        scooter.setStatus(status);
        scooter = scooterRepository.save(scooter);
        return modelMapper.map(scooter, ScooterResponseDto.class);
    }
}
