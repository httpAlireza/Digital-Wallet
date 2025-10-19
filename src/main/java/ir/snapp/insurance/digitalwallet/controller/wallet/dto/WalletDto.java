package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import ir.snapp.insurance.digitalwallet.enums.Currency;
import ir.snapp.insurance.digitalwallet.model.Wallet;

/**
 * DTO for wallet information
 *
 * @author Alireza Khodadoost
 */
public record WalletDto(
        Long id,
        String name,
        Currency currency,
        Double balance
) {
    public static WalletDto from(Wallet wallet) {
        return new WalletDto(
                wallet.getId(),
                wallet.getName(),
                wallet.getCurrency(),
                wallet.getBalance()
        );
    }
}

