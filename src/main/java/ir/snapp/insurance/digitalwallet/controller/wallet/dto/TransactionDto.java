package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import ir.snapp.insurance.digitalwallet.model.Transaction;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Transaction entity.
 *
 * @author Alireza Khodadoost
 */
public record TransactionDto(
        Long id,
        String type,
        Double amount,
        Long fromWalletId,
        Long toWalletId,
        LocalDateTime transactionDate) {
    public static TransactionDto fromEntity(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getType().name(),
                transaction.getAmount(),
                transaction.getFromWallet() != null ? transaction.getFromWallet().getId() : null,
                transaction.getToWallet() != null ? transaction.getToWallet().getId() : null,
                transaction.getCreatedAt()
        );
    }
}
