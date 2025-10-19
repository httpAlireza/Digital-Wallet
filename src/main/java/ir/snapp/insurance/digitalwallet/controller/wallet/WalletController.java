package ir.snapp.insurance.digitalwallet.controller.wallet;

import ir.snapp.insurance.digitalwallet.controller.wallet.dto.*;
import ir.snapp.insurance.digitalwallet.model.Transaction;
import ir.snapp.insurance.digitalwallet.model.Wallet;
import ir.snapp.insurance.digitalwallet.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<WalletDto> createWallet(@RequestBody WalletRequest request,
                                                  Principal principal) {
        Wallet wallet = walletService.createWallet(principal.getName(), request);
        return ResponseEntity.ok(WalletDto.from(wallet));
    }

    /**
     * Retrieves all wallets for the authenticated user.
     *
     * @param principal the security principal representing the authenticated user
     * @return a response entity containing a list of Wallets
     */
    @GetMapping
    public ResponseEntity<List<WalletDto>> getWallets(Principal principal) {
        List<WalletDto> wallets = walletService.getWallets(principal.getName())
                .stream().map(WalletDto::from)
                .toList();
        return ResponseEntity.ok(wallets);
    }

    /**
     * Filters transactions for a specific wallet based on provided criteria.
     *
     * @param params    the map of filter parameters
     * @param walletId  the ID of the wallet
     * @param principal the security principal representing the authenticated user
     * @return a response entity containing a paginated list of Transactions
     */
    @GetMapping("{walletId}/transactions")
    public ResponseEntity<Page<Transaction>> filterTransactions(
            @RequestParam Map<String, String> params,
            @PathVariable Long walletId,
            Principal principal) {
        var request = createTransactionFilterRequest(params);
        Page<Transaction> page = walletService.filterTransactions(principal.getName(), walletId, request);
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
    public ResponseEntity<String> deposit(@PathVariable Long walletId,
                                          @RequestBody DepositRequest request,
                                          Principal principal) {
        walletService.deposit(principal.getName(), walletId, request.getAmount());
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
    public ResponseEntity<String> withdraw(@PathVariable Long walletId,
                                           @RequestBody WithdrawRequest withdrawRequest,
                                           Principal principal) {
        walletService.withdraw(principal.getName(), walletId, withdrawRequest.getAmount());
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
    public ResponseEntity<Double> getBalance(@PathVariable Long walletId,
                                             Principal principal) {
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
    public ResponseEntity<String> transfer(@PathVariable Long walletId,
                                           @RequestBody TransferRequest request,
                                           Principal principal) {
        walletService.transfer(principal.getName(), walletId, request.getToWalletId(), request.getAmount());
        return ResponseEntity.ok("Transfer successful");
    }

    /**
     * Creates a TransactionFilterRequest from the provided parameters.
     *
     * @param params the map of filter parameters
     * @return the constructed TransactionFilterRequest
     */
    private static TransactionFilterRequest createTransactionFilterRequest(Map<String, String> params) {
        var request = new TransactionFilterRequest();
        if (params.containsKey("from")) {
            request.setFrom(LocalDateTime.parse(params.get("from")));
        }
        if (params.containsKey("to")) {
            request.setTo(LocalDateTime.parse(params.get("to")));
        }
        if (params.containsKey("page")) {
            request.setPage(Integer.parseInt(params.get("page")));
        }
        if (params.containsKey("size")) {
            request.setSize(Integer.parseInt(params.get("size")));
        }
        return request;
    }
}

