package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.application;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain.TransactionService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.dto.CreateTransactionRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.dto.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody CreateTransactionRequestDto transactionRequest) {
        TransactionResponseDto response = transactionService.createTransaction(transactionRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable Long transactionId) {
        TransactionResponseDto response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(response);
    }

}
