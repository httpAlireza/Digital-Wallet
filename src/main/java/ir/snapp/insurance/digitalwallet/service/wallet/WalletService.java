package ir.snapp.insurance.digitalwallet.service.wallet;

public interface WalletService {
    void deposit(String username, Long walletId, Double amount);

    void withdraw(String username, Long walletId, Double amount);

    void transfer(String username, Long fromWalletId, Long toWalletId, Double amount);
}
