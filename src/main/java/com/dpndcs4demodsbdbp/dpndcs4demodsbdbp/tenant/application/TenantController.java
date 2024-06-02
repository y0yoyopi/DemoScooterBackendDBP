package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.application;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.TenantService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.dto.TenantSelfResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.dto.PatchTenantInfoDto;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }


    @GetMapping("/me")
    public ResponseEntity<TenantSelfResponseDTO> getTenant() {
        return ResponseEntity.ok(tenantService.getTenantOwnInfo());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateTenant(@RequestBody PatchTenantInfoDto tenantSelfResponseDTO) {
        tenantService.updateTenant(tenantSelfResponseDTO);
        return ResponseEntity.ok().build();
    }
}
