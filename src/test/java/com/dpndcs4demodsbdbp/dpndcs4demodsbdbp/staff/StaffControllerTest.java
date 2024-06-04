package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.application.StaffController;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.domain.StaffService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.dto.CreateStaffRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.dto.StaffResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StaffControllerTest {

    @Mock
    private StaffService staffService;

    @InjectMocks
    private StaffController staffController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(staffController).build();
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
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(staffService, times(1)).createStaff(any(CreateStaffRequestDto.class));
    }

    @Test
    public void testGetStaffById() throws Exception {
        StaffResponseDto responseDto = new StaffResponseDto();
        responseDto.setId(1L);
        responseDto.setFirstName("John");
        responseDto.setLastName("Doe");
        responseDto.setEmail("john.doe@example.com");

        when(staffService.getStaffById(anyLong())).thenReturn(responseDto);

        mockMvc.perform(get("/staff/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(staffService, times(1)).getStaffById(anyLong());
    }

    @Test
    public void testUpdateStaff() throws Exception {
        CreateStaffRequestDto requestDto = new CreateStaffRequestDto();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setPassword("newpassword");

        StaffResponseDto responseDto = new StaffResponseDto();
        responseDto.setId(1L);
        responseDto.setFirstName("John");
        responseDto.setLastName("Doe");
        responseDto.setEmail("john.doe@example.com");

        when(staffService.updateStaff(anyLong(), any(CreateStaffRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/staff/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"password\":\"newpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(staffService, times(1)).updateStaff(anyLong(), any(CreateStaffRequestDto.class));
    }

    @Test
    public void testDeleteStaff() throws Exception {
        doNothing().when(staffService).deleteStaff(anyLong());

        mockMvc.perform(delete("/staff/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(staffService, times(1)).deleteStaff(anyLong());
    }
}
