package ir.snapp.insurance.digitalwallet.service.wallet;

import ir.snapp.insurance.digitalwallet.controller.wallet.dto.*;
import ir.snapp.insurance.digitalwallet.enums.Currency;
import ir.snapp.insurance.digitalwallet.enums.TransactionType;
import ir.snapp.insurance.digitalwallet.model.Transaction;
import ir.snapp.insurance.digitalwallet.model.User;
import ir.snapp.insurance.digitalwallet.model.Wallet;
import ir.snapp.insurance.digitalwallet.repository.TransactionRepository;
import ir.snapp.insurance.digitalwallet.repository.UserRepository;
import ir.snapp.insurance.digitalwallet.repository.WalletRepository;
import ir.snapp.insurance.digitalwallet.util.Paginated;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static ir.snapp.insurance.digitalwallet.exception.PredefinedError.*;

/**
 * Implementation of WalletService to handle wallet operations such as deposit, withdraw, and transfer.
 *
 * @author Alireza Khodadoost
 */
@Slf4j
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
    public WalletDto createWallet(String username, WalletCreationRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(USER_NOT_FOUND::getAppException);

        walletRepository.findByUserUsername(username).stream()
                .filter(w -> w.getName().equals(request.name()))
                .findAny()
                .ifPresent(w -> {
                    throw WALLET_ALREADY_EXISTS.getAppException();
                });

        Wallet wallet = new Wallet();
        wallet.setName(request.name());
        wallet.setCurrency(request.currency() != null ? request.currency() : Currency.IRR);
        wallet.setBalance(0.0);
        wallet.setUser(user);

        walletRepository.save(wallet);

        log.debug("Created wallet: {} for user: {}", wallet, username);
        return WalletDto.fromEntity(wallet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WalletDto> getWallets(String username) {
        return walletRepository.findByUserUsername(username)
                .stream()
                .map(WalletDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BalanceDto getBalance(String username, Long walletId) {
        Wallet wallet = findUserWallet(username, walletId);
        return new BalanceDto(walletId, wallet.getBalance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Paginated<TransactionDto> filterTransactions(String username, Long walletId, TransactionFilterCriteria criteria) {
        Wallet wallet = findUserWallet(username, walletId);

        Pageable pageable = PageRequest.of(criteria.getPage(),
                criteria.getSize(),
                Sort.by(Sort.Direction.ASC, "createdAt"));

        var transactions = transactionRepository.findByFromWalletIdOrToWalletIdAndCreatedAtBetween(
                        wallet.getId(),
                        wallet.getId(),
                        criteria.getFrom(),
                        criteria.getTo(),
                        pageable).stream()
                .map(TransactionDto::fromEntity)
                .toList();

        log.debug("trx size : {}", transactions);

        var totalElements = transactionRepository.countByFromWalletIdOrToWalletIdAndCreatedAtBetween(
                wallet.getId(),
                wallet.getId(),
                criteria.getFrom(),
                criteria.getTo()
        );

        return new Paginated<>(criteria.getPage(), criteria.getSize(), totalElements, transactions);
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

        log.debug("Deposited {} {} to wallet {} of user {}", amount, wallet.getCurrency(), walletId, username);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void withdraw(String username, Long walletId, Double amount) {
        Wallet wallet = findUserWallet(username, walletId);
        if (wallet.getBalance() < amount) {
            throw INSUFFICIENT_FUNDS.getAppException();
        }
        wallet.setBalance(wallet.getBalance() - amount);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setAmount(amount);
        transaction.setFromWallet(wallet);

        transactionRepository.save(transaction);
        walletRepository.save(wallet);

        log.debug("Withdraw {} {} from wallet {} of user {}", amount, wallet.getCurrency(), walletId, username);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void transfer(String username, Long fromWalletId, Long toWalletId, Double amount) {
        Wallet fromWallet = findUserWallet(username, fromWalletId);
        Wallet toWallet = walletRepository.findById(toWalletId)
                .orElseThrow(TARGET_WALLET_NOT_FOUND::getAppException);

        if (!fromWallet.getCurrency().equals(toWallet.getCurrency())) {
            throw CURRENCY_MISMATCH.getAppException();
        }

        if (fromWallet.getBalance() < amount)
            throw INSUFFICIENT_FUNDS.getAppException();

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

        log.debug("Transferred {} {} from wallet {} to wallet {} by user {}",
                amount, fromWallet.getCurrency(), fromWalletId, toWalletId, username);
    }

    private Wallet findUserWallet(String username, Long walletId) {
        return walletRepository.findByIdAndUserUsername(walletId, username)
                .orElseThrow(WALLET_NOT_FOUND::getAppException);
    }
}

