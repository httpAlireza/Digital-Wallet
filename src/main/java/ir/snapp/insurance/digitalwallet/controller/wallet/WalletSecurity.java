package ir.snapp.insurance.digitalwallet.controller.wallet;

import ir.snapp.insurance.digitalwallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Security component to verify wallet ownership.
 *
 * @author Alireza Khodadoost
 */
@Component
@RequiredArgsConstructor
public class WalletSecurity {

    private final WalletRepository walletRepository;

    public boolean isOwner(Long walletId, String username) {
        return walletRepository.existsByIdAndUserUsername(walletId, username);
    }
}
