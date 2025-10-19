package ir.snapp.insurance.digitalwallet.controller.wallet;

import ir.snapp.insurance.digitalwallet.controller.wallet.dto.TransferRequest;
import ir.snapp.insurance.digitalwallet.controller.wallet.dto.WalletRequest;
import ir.snapp.insurance.digitalwallet.model.Wallet;
import ir.snapp.insurance.digitalwallet.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody WalletRequest request, Principal principal) {
        Wallet wallet = walletService.createWallet(principal.getName(), request);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> getWallets(Principal principal) {
        List<Wallet> wallets = walletService.getWallets(principal.getName());
        return ResponseEntity.ok(wallets);
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long walletId, @RequestParam Double amount, Principal principal) {
        walletService.deposit(principal.getName(), walletId, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long walletId, @RequestParam Double amount, Principal principal) {
        walletService.withdraw(principal.getName(), walletId, amount);
        return ResponseEntity.ok("Withdrawal successful");
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Long walletId,Principal principal) {
        var balance = walletService.getBalance(principal.getName(), walletId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/{walletId}/transfer")
    public ResponseEntity<String> transfer(@PathVariable Long walletId, @RequestBody TransferRequest request, Principal principal) {
        walletService.transfer(principal.getName(), walletId, request.getToWalletId(), request.getAmount());
        return ResponseEntity.ok("Transfer successful");
    }
}

