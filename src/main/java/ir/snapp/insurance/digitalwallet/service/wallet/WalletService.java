package ir.snapp.insurance.digitalwallet.service.wallet;

import ir.snapp.insurance.digitalwallet.controller.wallet.dto.TransactionDto;
import ir.snapp.insurance.digitalwallet.controller.wallet.dto.TransactionFilterRequest;
import ir.snapp.insurance.digitalwallet.controller.wallet.dto.WalletCreationRequest;
import ir.snapp.insurance.digitalwallet.controller.wallet.dto.WalletDto;
import ir.snapp.insurance.digitalwallet.model.Transaction;
import ir.snapp.insurance.digitalwallet.model.Wallet;
import ir.snapp.insurance.digitalwallet.util.Paginated;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface for wallet operations such as deposit, withdraw, and transfer.
 *
 * @author Alireza Khodadoost
 */
public interface WalletService {

    /**
     * Creates a new wallet for the specified user.
     *
     * @param username the username of the wallet owner
     * @param request  the wallet creation request containing wallet details
     * @return the created Wallet
     */
    WalletDto createWallet(String username, WalletCreationRequest request);

    /**
     * Retrieves all wallets for the specified user.
     *
     * @param username the username of the wallet owner
     * @return a list of Wallets
     */
    List<WalletDto> getWallets(String username);

    /**
     * Retrieves the balance of the specified wallet for the user.
     *
     * @param username the username of the wallet owner
     * @param walletId the ID of the wallet
     * @return the balance of the wallet
     */
    Double getBalance(String username, Long walletId);

    /**
     * Filters transactions for the specified wallet based on the provided filter criteria.
     *
     * @param username the username of the wallet owner
     * @param walletId the ID of the wallet
     * @param request  the transaction filter request containing filter criteria
     * @return a paginated list of Transactions matching the filter criteria
     */
    Paginated<TransactionDto> filterTransactions(String username, Long walletId, TransactionFilterRequest request);

    /**
     * Deposits a specified amount into the user's wallet.
     *
     * @param username the username of the wallet owner
     * @param walletId the ID of the wallet
     * @param amount   the amount to deposit
     */
    void deposit(String username, Long walletId, Double amount);

    /**
     * Withdraws a specified amount from the user's wallet.
     *
     * @param username the username of the wallet owner
     * @param walletId the ID of the wallet
     * @param amount   the amount to withdraw
     */
    void withdraw(String username, Long walletId, Double amount);

    /**
     * Transfers a specified amount from one wallet to another.
     *
     * @param username     the username of the wallet owner
     * @param fromWalletId the ID of the wallet to transfer from
     * @param toWalletId   the ID of the wallet to transfer to
     * @param amount       the amount to transfer
     */
    void transfer(String username, Long fromWalletId, Long toWalletId, Double amount);
}
