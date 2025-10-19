package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import ir.snapp.insurance.digitalwallet.enums.Currency;

/**
 * DTO for wallet creation request
 *
 * @author Alireza Khodadoost
 */

public record WalletRequest(
        String name,
        Currency currency
) {
}

