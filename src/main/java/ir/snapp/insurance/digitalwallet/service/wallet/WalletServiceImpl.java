package ir.snapp.insurance.digitalwallet.service.wallet;

import ir.snapp.insurance.digitalwallet.controller.wallet.dto.TransactionFilterRequest;
import ir.snapp.insurance.digitalwallet.controller.wallet.dto.WalletRequest;
import ir.snapp.insurance.digitalwallet.enums.Currency;
import ir.snapp.insurance.digitalwallet.enums.TransactionType;
import ir.snapp.insurance.digitalwallet.model.Transaction;
import ir.snapp.insurance.digitalwallet.model.User;
import ir.snapp.insurance.digitalwallet.model.Wallet;
import ir.snapp.insurance.digitalwallet.repository.TransactionRepository;
import ir.snapp.insurance.digitalwallet.repository.UserRepository;
import ir.snapp.insurance.digitalwallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of WalletService to handle wallet operations such as deposit, withdraw, and transfer.
 *
 * @author Alireza Khodadoost
 */
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Wallet createWallet(String username, WalletRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Wallet wallet = new Wallet();
        wallet.setName(request.name());
        wallet.setCurrency(request.currency() != null ? request.currency() : Currency.IRR);
        wallet.setBalance(0.0);
        wallet.setUser(user);

        return walletRepository.save(wallet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Wallet> getWallets(String username) {
        return walletRepository.findByUserUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getBalance(String username, Long walletId) {
        Wallet wallet = findUserWallet(username, walletId);
        return wallet.getBalance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Transaction> filterTransactions(String username, Long walletId, TransactionFilterRequest request) {
        Wallet wallet = findUserWallet(username, walletId);

        Pageable pageable = PageRequest.of(request.getPage(),
                request.getSize(),
                Sort.by(Sort.Direction.ASC, "createdAt"));

        return transactionRepository.findByFromWalletIdOrToWalletIdAndCreatedAtBetween(
                wallet.getId(),
                wallet.getId(),
                request.getFrom(),
                request.getTo(),
                pageable
        );
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void deposit(String username, Long walletId, Double amount) {
        Wallet wallet = findUserWallet(username, walletId);
        wallet.setBalance(wallet.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setToWallet(wallet);

        transactionRepository.save(transaction);
        walletRepository.save(wallet);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void withdraw(String username, Long walletId, Double amount) {
        Wallet wallet = findUserWallet(username, walletId);
        if (wallet.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        wallet.setBalance(wallet.getBalance() - amount);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setAmount(amount);
        transaction.setFromWallet(wallet);

        transactionRepository.save(transaction);
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

        if (!fromWallet.getCurrency().equals(toWallet.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch between wallets");
        }

        if (fromWallet.getBalance() < amount)
            throw new IllegalArgumentException("Insufficient funds");

        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(amount);
        transaction.setFromWallet(fromWallet);
        transaction.setToWallet(toWallet);

        transactionRepository.save(transaction);
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }

    private Wallet findUserWallet(String username, Long walletId) {
        return walletRepository.findByIdAndUserUsername(walletId, username)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
    }
}

