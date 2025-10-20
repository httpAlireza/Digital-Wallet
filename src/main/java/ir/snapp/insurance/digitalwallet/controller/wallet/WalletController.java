package ir.snapp.insurance.digitalwallet.controller.wallet;

import ir.snapp.insurance.digitalwallet.controller.wallet.dto.*;
import ir.snapp.insurance.digitalwallet.service.wallet.WalletService;
import ir.snapp.insurance.digitalwallet.util.Paginated;
import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST controller for managing wallet-related operations for authenticated users.
 * <p>
 * Provides endpoints to:
 * <ul>
 *     <li>Create a new wallet</li>
 *     <li>Retrieve wallets for the authenticated user</li>
 *     <li>Retrieve wallet details</li>
 *     <li>Perform deposit, withdrawal, and transfer operations</li>
 *     <li>Filter transactions for a specific wallet</li>
 * </ul>
 * <p>
 * All endpoints require authentication unless explicitly permitted (e.g., login/signup).
 * Ownership of the wallet is verified using {@code @PreAuthorize} annotations.
 * </p>
 * <p>
 * This controller adheres to RESTful API conventions, where resources are primarily
 * represented by wallets and transactions, and actions on wallets are represented
 * as sub-resources or operations on transactions.
 * </p>
 *
 * @author Alireza Khodadoust
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
     * @param request   the wallet creation request containing wallet name, currency, and initial balance
     * @param principal the security principal representing the authenticated user
     * @return {@code ResponseEntity} containing the created {@link WalletDto} with HTTP status 201 (Created) on success
     */
    @PostMapping
    public ResponseEntity<WalletDto> createWallet(
            @Validated(ValidationGroups.ValidationSeq.class) @RequestBody WalletCreationRequest request,
            Principal principal) {
        log.debug("Received request to create wallet {} for user: {}", request, principal.getName());
        WalletDto wallet = walletService.createWallet(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    /**
     * Retrieves all wallets belonging to the authenticated user.
     *
     * @param principal the security principal representing the authenticated user
     * @return {@code ResponseEntity} containing a list of {@link WalletDto} objects
     */
    @GetMapping
    public ResponseEntity<List<WalletDto>> getWallets(Principal principal) {
        log.debug("Received request to get wallets for user: {}", principal.getName());
        List<WalletDto> wallets = walletService.getWallets(principal.getName());
        return ResponseEntity.ok(wallets);
    }

    /**
     * Retrieves a specific wallet belonging to the authenticated user.
     *
     * @param walletId  the ID of the wallet
     * @param principal the security principal representing the authenticated user
     * @return {@code ResponseEntity} containing the {@link WalletDto} object
     */
    @GetMapping("/{walletId}")
    @PreAuthorize("@walletSecurity.isOwner(#walletId, principal.getUsername())")
    public ResponseEntity<WalletDto> getWallet(
            @PathVariable Long walletId,
            Principal principal) {
        log.debug("Received request to get wallet {} for user: {}", walletId, principal.getName());
        var wallet = walletService.getWallet(principal.getName(), walletId);
        return ResponseEntity.ok(wallet);
    }

    /**
     * Filters transactions for a specific wallet based on the provided criteria.
     *
     * @param filterCriteria the transaction filtering parameters (e.g., date range, pagination)
     * @param walletId       the ID of the wallet
     * @param principal      the security principal representing the authenticated user
     * @return {@code ResponseEntity} containing a paginated list of {@link TransactionDto}
     */
    @GetMapping("/{walletId}/transactions")
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
     * Deposits a specified amount into the authenticated user's wallet.
     *
     * @param walletId  the ID of the wallet
     * @param request   the deposit request containing the amount
     * @param principal the security principal representing the authenticated user
     * @return {@code ResponseEntity} with a success message
     */
    @PostMapping("/{walletId}/deposit")
    @PreAuthorize("@walletSecurity.isOwner(#walletId, principal.getUsername())")
    public ResponseEntity<String> deposit(
            @PathVariable Long walletId,
            @Validated(ValidationGroups.ValidationSeq.class) @RequestBody DepositRequest request,
            Principal principal) {
        log.debug("Received request to deposit into wallet {} for user: {}", walletId, principal.getName());
        walletService.deposit(principal.getName(), walletId, request.amount());
        return ResponseEntity.ok("Deposit successful");
    }

    /**
     * Withdraws a specified amount from the authenticated user's wallet.
     *
     * @param walletId        the ID of the wallet
     * @param withdrawRequest the withdrawal request containing the amount
     * @param principal       the security principal representing the authenticated user
     * @return {@code ResponseEntity} with a success message
     */
    @PostMapping("/{walletId}/withdraw")
    @PreAuthorize("@walletSecurity.isOwner(#walletId, principal.getUsername())")
    public ResponseEntity<String> withdraw(
            @PathVariable Long walletId,
            @Validated(ValidationGroups.ValidationSeq.class) @RequestBody WithdrawRequest withdrawRequest,
            Principal principal) {
        log.debug("Received request to withdraw from wallet {} for user: {}", walletId, principal.getName());
        walletService.withdraw(principal.getName(), walletId, withdrawRequest.amount());
        return ResponseEntity.ok("Withdrawal successful");
    }

    /**
     * Transfers a specified amount from one wallet to another.
     *
     * @param walletId  the ID of the source wallet
     * @param request   the transfer request containing the destination wallet ID and amount
     * @param principal the security principal representing the authenticated user
     * @return {@code ResponseEntity} with a success message
     */
    @PostMapping("/{walletId}/transfer")
    @PreAuthorize("@walletSecurity.isOwner(#walletId, principal.getUsername())")
    public ResponseEntity<String> transfer(
            @PathVariable Long walletId,
            @Validated(ValidationGroups.ValidationSeq.class) @RequestBody TransferRequest request,
            Principal principal) {
        log.debug("Received request to transfer from wallet {} for user: {}", walletId, principal.getName());
        walletService.transfer(principal.getName(), walletId, request.toWalletId(), request.amount());
        return ResponseEntity.ok("Transfer successful");
    }
}
