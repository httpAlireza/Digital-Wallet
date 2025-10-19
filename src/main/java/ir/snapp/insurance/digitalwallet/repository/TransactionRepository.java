package ir.snapp.insurance.digitalwallet.repository;


import ir.snapp.insurance.digitalwallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromWalletIdOrToWalletId(Long fromWalletId, Long toWalletId);
}

