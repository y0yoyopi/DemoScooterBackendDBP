package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.application.TransactionController;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain.TransactionService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.dto.CreateTransactionRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.dto.TransactionResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testCreateTransaction() throws Exception {
        CreateTransactionRequestDto requestDto = new CreateTransactionRequestDto();
        requestDto.setAmount(100.0);
        requestDto.setRideId(1L);
        requestDto.setTenantId(1L);

        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setId(1L);
        responseDto.setAmount(100.0);
        responseDto.setRideId(1L);
        responseDto.setTenantId(1L);
        responseDto.setTransactionDateTime(LocalDateTime.now());

        when(transactionService.createTransaction(any(CreateTransactionRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100.0, \"rideId\": 1, \"tenantId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.rideId").value(1L))
                .andExpect(jsonPath("$.tenantId").value(1L));

        verify(transactionService, times(1)).createTransaction(any(CreateTransactionRequestDto.class));
    }

    @Test
    public void testGetTransactionById() throws Exception {
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setId(1L);
        responseDto.setAmount(100.0);
        responseDto.setRideId(1L);
        responseDto.setTenantId(1L);
        responseDto.setTransactionDateTime(LocalDateTime.now());

        when(transactionService.getTransactionById(anyLong())).thenReturn(responseDto);

        mockMvc.perform(get("/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.rideId").value(1L))
                .andExpect(jsonPath("$.tenantId").value(1L));

        verify(transactionService, times(1)).getTransactionById(anyLong());
    }
}
