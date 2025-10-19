package ir.snapp.insurance.digitalwallet.controller.wallet;

import ir.snapp.insurance.digitalwallet.controller.wallet.dto.*;
import ir.snapp.insurance.digitalwallet.service.wallet.WalletService;
import ir.snapp.insurance.digitalwallet.util.Paginated;
import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST controller for managing wallet operations such as creation, deposit, withdrawal, balance inquiry, and transfer.
 *
 * @author Alireza Khodadoost
 */
@Slf4j
@RestController
@RequestMapping("/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    /**
     * Creates a new wallet for the authenticated user.
     *
     * @param request   the wallet creation request containing wallet details
     * @param principal the security principal representing the authenticated user
     * @return a response entity containing the created Wallet
     */
    @PostMapping
    public ResponseEntity<WalletDto> createWallet(
            @Validated(ValidationGroups.ValidationSeq.class) @RequestBody WalletCreationRequest request,
            Principal principal) {
        log.debug("Received request to Create Wallet {} for user: {}", request, principal.getName());
        WalletDto wallet = walletService.createWallet(principal.getName(), request);
        return ResponseEntity.ok(wallet);
    }

    /**
     * Retrieves all wallets for the authenticated user.
     *
     * @param principal the security principal representing the authenticated user
     * @return a response entity containing a list of Wallets
     */
    @GetMapping
    public ResponseEntity<List<WalletDto>> getWallets(Principal principal) {
        log.debug("Received request to get wallets for user: {}", principal.getName());
        List<WalletDto> wallets = walletService.getWallets(principal.getName());
        return ResponseEntity.ok(wallets);
    }

    /**
     * Filters transactions for a specific wallet based on provided criteria.
     *
     * @param filterCriteria the transaction filter criteria
     * @param walletId       the ID of the wallet
     * @param principal      the security principal representing the authenticated user
     * @return a response entity containing a paginated list of Transactions
     */
    @GetMapping("{walletId}/transactions")
    @PreAuthorize("@walletSecurity.isOwner(#walletId, principal.getUsername())")
    public ResponseEntity<Paginated<TransactionDto>> filterTransactions(
            @Validated(ValidationGroups.ValidationSeq.class) TransactionFilterCriteria filterCriteria,
            @PathVariable Long walletId,
            Principal principal) {
        log.debug("Received request to filter transactions for wallet {} for user: {}", walletId, principal.getName());
        Paginated<TransactionDto> page = walletService.filterTransactions(principal.getName(), walletId, filterCriteria);
        return ResponseEntity.ok(page);
    }

    /**
     * Deposits a specified amount into the user's wallet.
     *
     * @param walletId  the ID of the wallet
     * @param request   the deposit request containing the amount to deposit
     * @param principal the security principal representing the authenticated user
     * @return a response entity indicating the success of the deposit operation
     */
    @PostMapping("/{walletId}/deposit")
    @PreAuthorize("@walletSecurity.isOwner(#walletId, principal.getUsername())")
    public ResponseEntity<String> deposit(
            @PathVariable Long walletId,
            @Validated(ValidationGroups.ValidationSeq.class) @RequestBody DepositRequest request,
            Principal principal) {
        log.debug("Received request to deposit wallet {} for user: {}", walletId, principal.getName());
        walletService.deposit(principal.getName(), walletId, request.amount());
        return ResponseEntity.ok("Deposit successful");
    }

    /**
     * Withdraws a specified amount from the user's wallet.
     *
     * @param walletId        the ID of the wallet
     * @param withdrawRequest the withdrawal request containing the amount to withdraw
     * @param principal       the security principal representing the authenticated user
     * @return a response entity indicating the success of the withdrawal operation
     */
    @PostMapping("/{walletId}/withdraw")
    @PreAuthorize("@walletSecurity.isOwner(#walletId, principal.getUsername())")
    public ResponseEntity<String> withdraw(
            @PathVariable Long walletId,
            @Validated(ValidationGroups.ValidationSeq.class) @RequestBody WithdrawRequest withdrawRequest,
            Principal principal) {
        log.debug("Received request to withdraw wallet {} for user: {}", walletId, principal.getName());
        walletService.withdraw(principal.getName(), walletId, withdrawRequest.amount());
        return ResponseEntity.ok("Withdrawal successful");
    }

    /**
     * Retrieves the balance of the specified wallet for the authenticated user.
     *
     * @param walletId  the ID of the wallet
     * @param principal the security principal representing the authenticated user
     * @return a response entity containing the balance of the wallet
     */
    @GetMapping("/{walletId}/balance")
    @PreAuthorize("@walletSecurity.isOwner(#walletId, principal.getUsername())")
    public ResponseEntity<BalanceDto> getBalance(@PathVariable Long walletId,
                                             Principal principal) {
        log.debug("Received request to get wallet {} for user: {}", walletId, principal.getName());
        var balance = walletService.getBalance(principal.getName(), walletId);
        return ResponseEntity.ok(balance);
    }

    /**
     * Transfers a specified amount from one wallet to another.
     *
     * @param walletId  the ID of the source wallet
     * @param request   the transfer request containing the destination wallet ID and amount
     * @param principal the security principal representing the authenticated user
     * @return a response entity indicating the success of the transfer operation
     */
    @PostMapping("/{walletId}/transfer")
    @PreAuthorize("@walletSecurity.isOwner(#walletId, principal.getUsername())")
    public ResponseEntity<String> transfer(
            @PathVariable Long walletId,
            @Validated(ValidationGroups.ValidationSeq.class) @RequestBody TransferRequest request,
            Principal principal) {
        log.debug("Received request to transfer wallet {} for user: {}", walletId, principal.getName());
        walletService.transfer(principal.getName(), walletId, request.toWalletId(), request.amount());
        return ResponseEntity.ok("Transfer successful");
    }
}

