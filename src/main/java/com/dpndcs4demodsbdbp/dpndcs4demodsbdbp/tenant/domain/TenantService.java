package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils.AuthorizationUtils;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.exceptions.ResourceNotFoundException;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.domain.ParkingArea;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.parkingarea.infrastructure.ParkingAreaRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.infrastructure.StaffRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.dto.PatchTenantInfoDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.dto.TenantSelfResponseDTO;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.exceptions.UnauthorizeOperationException;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.infrastructure.TenantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final ModelMapper modelMapper;
    private final ParkingAreaRepository parkingarearepository;
    private final AuthorizationUtils authorizationUtils;
    private final StaffRepository staffrepository;


    @Autowired
    public TenantService(TenantRepository tenantRepository, ModelMapper modelMapper, ParkingAreaRepository parkingarearepository, AuthorizationUtils authorizationUtils, StaffRepository staffrepository) {
        this.tenantRepository = tenantRepository;
        this.modelMapper = modelMapper;
        this.parkingarearepository = parkingarearepository;
        this.authorizationUtils = authorizationUtils;
        this.staffrepository = staffrepository;
    }

    public TenantSelfResponseDTO getTenantOwnInfo() {
        String email = authorizationUtils.getCurrentUserEmail();
        Tenant tenant = tenantRepository.findByEmail(email).orElseThrow(() -> new UnauthorizeOperationException("Unathorized User"));
        return modelMapper.map(tenant, TenantSelfResponseDTO.class);

    }

    public void deleteTenant(Long id) {
        // Revisa si el usuario es el mismo
        if(!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizeOperationException("You do not have permission to delete this tenant");

        if (!tenantRepository.existsById(id))
            throw new ResourceNotFoundException("Tenant not found");

        tenantRepository.deleteById(id);
    }

    public void updateTenant(PatchTenantInfoDto tenantSelfResponseDTO) {
        // Here get the current user identifier (email) using Spring Security
        String username = "email";

        Tenant tenant = tenantRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Tenant not found"));

        tenant.setFirstName(tenantSelfResponseDTO.getFirstName());
        tenant.setLastName(tenantSelfResponseDTO.getLastName());
        tenant.setPhoneNumber(tenantSelfResponseDTO.getPhoneNumber());

        tenantRepository.save(tenant);
    }
}
