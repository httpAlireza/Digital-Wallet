package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import ir.snapp.insurance.digitalwallet.enums.Currency;
import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for wallet creation request
 *
 * @author Alireza Khodadoost
 */

public record WalletCreationRequest(
        @NotBlank(message = "name.is_required", groups = ValidationGroups.Presence.class)
        String name,
        @NotBlank(message = "currency.is_required", groups = ValidationGroups.Presence.class)
        Currency currency
) {
}

