package ir.snapp.insurance.digitalwallet.service.wallet;

import ir.snapp.insurance.digitalwallet.model.Wallet;
import ir.snapp.insurance.digitalwallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of WalletService to handle wallet operations such as deposit, withdraw, and transfer.
 *
 * @author Alireza Khodadoost
 */
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    /**
     * {@inheritDoc}
     */
    public void deposit(String username, Long walletId, Double amount) {
        Wallet wallet = findUserWallet(username, walletId);
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
    }

    /**
     * {@inheritDoc}
     */
    public void withdraw(String username, Long walletId, Double amount) {
        Wallet wallet = findUserWallet(username, walletId);
        if (wallet.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void transfer(String username, Long fromWalletId, Long toWalletId, Double amount) {
        Wallet fromWallet = findUserWallet(username, fromWalletId);
        Wallet toWallet = walletRepository.findById(toWalletId)
                .orElseThrow(() -> new IllegalArgumentException("Target wallet not found"));

        if (fromWallet.getBalance() < amount)
            throw new IllegalArgumentException("Insufficient funds");

        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }

    private Wallet findUserWallet(String username, Long walletId) {
        return walletRepository.findByIdAndUserUsername(walletId, username)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
    }
}

