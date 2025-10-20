package ir.snapp.insurance.digitalwallet.repository;


import ir.snapp.insurance.digitalwallet.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Transaction entity operations.
 *
 * @author Alireza Khodadoost
 */
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

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

