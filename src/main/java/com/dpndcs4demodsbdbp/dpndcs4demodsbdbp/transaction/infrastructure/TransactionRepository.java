package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.infrastructure;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain.Transaction;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
