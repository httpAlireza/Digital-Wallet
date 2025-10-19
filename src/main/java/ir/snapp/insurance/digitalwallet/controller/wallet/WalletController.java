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

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody WalletRequest request,
                                               Principal principal) {
        Wallet wallet = walletService.createWallet(principal.getName(), request);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> getWallets(Principal principal) {
        List<Wallet> wallets = walletService.getWallets(principal.getName());
        return ResponseEntity.ok(wallets);
    }

    @GetMapping("{walletId}/transactions")
    public ResponseEntity<Page<Transaction>> filterTransactions(
            @RequestParam Map<String, String> params,
            @PathVariable Long walletId,
            Principal principal) {
        var request = createTransactionFilterRequest(params);
        Page<Transaction> page = walletService.filterTransactions(principal.getName(), walletId, request);
        return ResponseEntity.ok(page);
    }


    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long walletId,
                                          @RequestBody DepositRequest request,
                                          Principal principal) {
        walletService.deposit(principal.getName(), walletId, request.getAmount());
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long walletId,
                                           @RequestBody WithdrawRequest withdrawRequest,
                                           Principal principal) {
        walletService.withdraw(principal.getName(), walletId, withdrawRequest.getAmount());
        return ResponseEntity.ok("Withdrawal successful");
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Long walletId,
                                             Principal principal) {
        var balance = walletService.getBalance(principal.getName(), walletId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/{walletId}/transfer")
    public ResponseEntity<String> transfer(@PathVariable Long walletId,
                                           @RequestBody TransferRequest request,
                                           Principal principal) {
        walletService.transfer(principal.getName(), walletId, request.getToWalletId(), request.getAmount());
        return ResponseEntity.ok("Transfer successful");
    }

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

