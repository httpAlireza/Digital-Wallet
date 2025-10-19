package ir.snapp.insurance.digitalwallet.repository;


import ir.snapp.insurance.digitalwallet.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromWalletIdOrToWalletIdAndCreatedAtBetween(
            Long fromWalletId,
            Long toWalletId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );

    Long countByFromWalletIdOrToWalletIdAndCreatedAtBetween(
            Long fromWalletId,
            Long toWalletId,
            LocalDateTime start,
            LocalDateTime end
    );
}

