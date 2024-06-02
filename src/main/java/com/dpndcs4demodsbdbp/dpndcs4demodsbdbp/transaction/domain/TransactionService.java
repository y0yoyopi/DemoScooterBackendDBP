package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils.AuthorizationUtils;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.infrastructure.RideRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.exceptions.UnauthorizeOperationException;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.infrastructure.TenantRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.dto.CreateTransactionRequestDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.dto.TransactionResponseDto;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.infrastructure.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RideRepository rideRepository;
    private final TenantRepository tenantRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, RideRepository rideRepository, TenantRepository tenantRepository, AuthorizationUtils authorizationUtils) {
        this.transactionRepository = transactionRepository;
        this.rideRepository = rideRepository;
        this.tenantRepository = tenantRepository;
        this.authorizationUtils = authorizationUtils;
    }

    public TransactionResponseDto createTransaction(CreateTransactionRequestDto transactionRequest) {
        if (!authorizationUtils.isAdminOrStaff()) {
            throw new UnauthorizeOperationException("You do not have permission to create a transaction");
        }

        Ride ride = rideRepository.findById(transactionRequest.getRideId())
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        Tenant tenant = tenantRepository.findById(transactionRequest.getTenantId())
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setRide(ride);
        transaction.setTenant(tenant);
        transaction.setTransactionDateTime(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);

        return modelMapper.map(transaction, TransactionResponseDto.class);
    }

    public TransactionResponseDto getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        return modelMapper.map(transaction, TransactionResponseDto.class);
    }

}
