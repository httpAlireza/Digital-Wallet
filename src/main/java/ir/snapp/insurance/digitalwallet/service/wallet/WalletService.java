package ir.snapp.insurance.digitalwallet.service.wallet;

/**
 * Service interface for wallet operations such as deposit, withdraw, and transfer.
 *
 * @author Alireza Khodadoost
 */
public interface WalletService {

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
