package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils.AuthorizationUtils;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.CreateParkingAreaRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.dto.ParkingAreaResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.infrastructure.ParkingAreaRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.exceptions.UnauthorizeOperationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingAreaService {

    private final ParkingAreaRepository parkingAreaRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository, AuthorizationUtils authorizationUtils) {
        this.parkingAreaRepository = parkingAreaRepository;
        this.authorizationUtils = authorizationUtils;
    }

    public ParkingAreaResponseDto createParkingArea(CreateParkingAreaRequestDto parkingAreaRequest) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to create a parking area.");
        }

        ParkingArea parkingArea = modelMapper.map(parkingAreaRequest, ParkingArea.class);
        parkingArea = parkingAreaRepository.save(parkingArea);
        return modelMapper.map(parkingArea, ParkingAreaResponseDto.class);
    }

    public ParkingAreaResponseDto getParkingAreaById(Long parkingAreaId) {
        ParkingArea parkingArea = parkingAreaRepository.findById(parkingAreaId)
                .orElseThrow(() -> new IllegalArgumentException("Parking Area not found"));
        return modelMapper.map(parkingArea, ParkingAreaResponseDto.class);
    }

    public List<ParkingAreaResponseDto> getAllParkingAreas() {
        return parkingAreaRepository.findAll().stream()
                .map(parkingArea -> modelMapper.map(parkingArea, ParkingAreaResponseDto.class))
                .collect(Collectors.toList());
    }

    public ParkingAreaResponseDto updateParkingArea(Long parkingAreaId, CreateParkingAreaRequestDto parkingAreaRequest) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to update this parking area.");
        }

        ParkingArea parkingArea = parkingAreaRepository.findById(parkingAreaId)
                .orElseThrow(() -> new IllegalArgumentException("Parking Area not found"));

        parkingArea.setLatitude(parkingAreaRequest.getLatitude());
        parkingArea.setLongitude(parkingAreaRequest.getLongitude());

        parkingArea = parkingAreaRepository.save(parkingArea);
        return modelMapper.map(parkingArea, ParkingAreaResponseDto.class);
    }

    public void deleteParkingArea(Long parkingAreaId) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to delete this parking area.");
        }

        ParkingArea parkingArea = parkingAreaRepository.findById(parkingAreaId)
                .orElseThrow(() -> new IllegalArgumentException("Parking Area not found"));
        parkingAreaRepository.delete(parkingArea);
    }
}
