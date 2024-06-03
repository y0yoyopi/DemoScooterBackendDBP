package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.application.StaffController;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StaffService staffService;

    @InjectMocks
    private StaffController staffController;

    private StaffResponseDto staffResponseDto;
    private CreateStaffRequestDto createStaffRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        staffResponseDto = new StaffResponseDto();
        staffResponseDto.setId(1L);
        staffResponseDto.setFirstName("John");
        staffResponseDto.setLastName("Doe");
        staffResponseDto.setEmail("john.doe@example.com");
        staffResponseDto.setRole(com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain.Role.STAFF);

        createStaffRequestDto = new CreateStaffRequestDto();
        createStaffRequestDto.setFirstName("John");
        createStaffRequestDto.setLastName("Doe");
        createStaffRequestDto.setEmail("john.doe@example.com");
        createStaffRequestDto.setPassword("password");
    }

    @Test
    public void testCreateStaff() throws Exception {
        when(staffService.createStaff(any(CreateStaffRequestDto.class))).thenReturn(staffResponseDto);

        mockMvc.perform(post("/staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createStaffRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    StaffResponseDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), StaffResponseDto.class);
                    assertEquals(staffResponseDto.getId(), response.getId());
                    assertEquals(staffResponseDto.getFirstName(), response.getFirstName());
                    assertEquals(staffResponseDto.getLastName(), response.getLastName());
                    assertEquals(staffResponseDto.getEmail(), response.getEmail());
                    assertEquals(staffResponseDto.getRole(), response.getRole());
                });
    }

    @Test
    public void testGetStaffById() throws Exception {
        when(staffService.getStaffById(anyLong())).thenReturn(staffResponseDto);

        mockMvc.perform(get("/staff/{staffId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    StaffResponseDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), StaffResponseDto.class);
                    assertEquals(staffResponseDto.getId(), response.getId());
                    assertEquals(staffResponseDto.getFirstName(), response.getFirstName());
                    assertEquals(staffResponseDto.getLastName(), response.getLastName());
                    assertEquals(staffResponseDto.getEmail(), response.getEmail());
                    assertEquals(staffResponseDto.getRole(), response.getRole());
                });
    }

    @Test
    public void testUpdateStaff() throws Exception {
        when(staffService.updateStaff(anyLong(), any(CreateStaffRequestDto.class))).thenReturn(staffResponseDto);

        mockMvc.perform(put("/staff/{staffId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createStaffRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    StaffResponseDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), StaffResponseDto.class);
                    assertEquals(staffResponseDto.getId(), response.getId());
                    assertEquals(staffResponseDto.getFirstName(), response.getFirstName());
                    assertEquals(staffResponseDto.getLastName(), response.getLastName());
                    assertEquals(staffResponseDto.getEmail(), response.getEmail());
                    assertEquals(staffResponseDto.getRole(), response.getRole());
                });
    }

    @Test
    public void testDeleteStaff() throws Exception {
        mockMvc.perform(delete("/staff/{staffId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
