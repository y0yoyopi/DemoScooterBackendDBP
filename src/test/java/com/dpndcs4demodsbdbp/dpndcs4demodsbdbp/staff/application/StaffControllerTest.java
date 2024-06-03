package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.application;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.domain.Staff;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.domain.StaffService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.dto.CreateStaffRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.dto.StaffResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StaffService staffService;

    @InjectMocks
    private StaffController staffController;

    @Autowired
    private ObjectMapper objectMapper;

    private Staff staff;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        staff = new Staff();
        staff.setFirstName("John");
        staff.setLastName("Doe");
        staff.setEmail("john.doe@example.com");
        staff.setPassword("password");
    }

    @Test
    public void testCreateStaff() throws Exception {
        CreateStaffRequestDto requestDto = new CreateStaffRequestDto();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setPassword("password");

        StaffResponseDto responseDto = new StaffResponseDto();
        responseDto.setId(1L);
        responseDto.setFirstName("John");
        responseDto.setLastName("Doe");
        responseDto.setEmail("john.doe@example.com");

        when(staffService.createStaff(any(CreateStaffRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(staffService, times(1)).createStaff(any(CreateStaffRequestDto.class));
    }

    @Test
    public void testGetStaffById() throws Exception {
        Long staffId = 1L;

        StaffResponseDto responseDto = new StaffResponseDto();
        responseDto.setId(staffId);
        responseDto.setFirstName("John");
        responseDto.setLastName("Doe");
        responseDto.setEmail("john.doe@example.com");

        when(staffService.getStaffById(staffId)).thenReturn(responseDto);

        mockMvc.perform(get("/staff/{staffId}", staffId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(staffService, times(1)).getStaffById(staffId);
    }

    @Test
    public void testUpdateStaff() throws Exception {
        Long staffId = 1L;
        CreateStaffRequestDto requestDto = new CreateStaffRequestDto();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setPassword("password");

        StaffResponseDto responseDto = new StaffResponseDto();
        responseDto.setId(staffId);
        responseDto.setFirstName("John");
        responseDto.setLastName("Doe");
        responseDto.setEmail("john.doe@example.com");

        when(staffService.updateStaff(eq(staffId), any(CreateStaffRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/staff/{staffId}", staffId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(staffService, times(1)).updateStaff(eq(staffId), any(CreateStaffRequestDto.class));
    }

    @Test
    public void testDeleteStaff() throws Exception {
        Long staffId = 1L;

        doNothing().when(staffService).deleteStaff(staffId);

        mockMvc.perform(delete("/staff/{staffId}", staffId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(staffService, times(1)).deleteStaff(staffId);
    }
}
