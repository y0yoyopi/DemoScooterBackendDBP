package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.application.TenantController;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.TenantService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.dto.PatchTenantInfoDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.dto.TenantSelfResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TenantControllerTest {

    @Mock
    private TenantService tenantService;

    @InjectMocks
    private TenantController tenantController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tenantController).build();
    }

    @Test
    public void testGetTenant() throws Exception {
        TenantSelfResponseDTO responseDto = new TenantSelfResponseDTO();
        responseDto.setFirstName("Jane");
        responseDto.setLastName("Doe");
        responseDto.setEmail("jane.doe@example.com");
        responseDto.setPhoneNumber("123456789");

        when(tenantService.getTenantOwnInfo()).thenReturn(responseDto);

        mockMvc.perform(get("/tenant/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"));

        verify(tenantService, times(1)).getTenantOwnInfo();
    }

    @Test
    public void testDeleteTenant() throws Exception {
        doNothing().when(tenantService).deleteTenant(anyLong());

        mockMvc.perform(delete("/tenant/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tenantService, times(1)).deleteTenant(anyLong());
    }

    @Test
    public void testUpdateTenant() throws Exception {
        PatchTenantInfoDto requestDto = new PatchTenantInfoDto();
        requestDto.setFirstName("Jane");
        requestDto.setLastName("Doe");
        requestDto.setPhoneNumber("987654321");

        doNothing().when(tenantService).updateTenant(any(PatchTenantInfoDto.class));

        mockMvc.perform(patch("/tenant/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"phoneNumber\":\"987654321\"}"))
                .andExpect(status().isOk());

        verify(tenantService, times(1)).updateTenant(any(PatchTenantInfoDto.class));
    }
}
