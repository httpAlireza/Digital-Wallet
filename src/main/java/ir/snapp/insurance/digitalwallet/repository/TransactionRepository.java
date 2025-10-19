package ir.snapp.insurance.digitalwallet.repository;


import ir.snapp.insurance.digitalwallet.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByFromWalletIdOrToWalletIdAndCreatedAtBetween(
            Long fromWalletId,
            Long toWalletId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}

