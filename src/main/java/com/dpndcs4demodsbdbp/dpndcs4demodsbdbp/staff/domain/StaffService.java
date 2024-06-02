package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.domain.AuthService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils.AuthorizationUtils;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.dto.CreateStaffRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.dto.StaffResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.infrastructure.StaffRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.exceptions.UnauthorizeOperationException;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public StaffService(StaffRepository staffRepository, PasswordEncoder passwordEncoder,AuthorizationUtils authorizationUtils) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorizationUtils = authorizationUtils;
    }

    public StaffResponseDto createStaff(CreateStaffRequestDto staffRequest) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to create a staff member");
        }
        Staff staff = modelMapper.map(staffRequest, Staff.class);
        staff.setPassword(passwordEncoder.encode(staffRequest.getPassword()));
        staff.setRole(Role.STAFF);

        staff = staffRepository.save(staff);
        return modelMapper.map(staff, StaffResponseDto.class);
    }

    public StaffResponseDto getStaffById(Long staffId) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to get a staff member");
        }
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));
        return modelMapper.map(staff, StaffResponseDto.class);
    }

    public StaffResponseDto updateStaff(Long staffId, CreateStaffRequestDto staffRequest) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to update a staff member");
        }
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        modelMapper.map(staffRequest, staff);
        staff.setPassword(passwordEncoder.encode(staffRequest.getPassword()));
        staff.setRole(Role.STAFF);

        staff = staffRepository.save(staff);
        return modelMapper.map(staff, StaffResponseDto.class);
    }

    public void deleteStaff(Long staffId) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to delete a staff member");
        }
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));
        staffRepository.delete(staff);
    }
}