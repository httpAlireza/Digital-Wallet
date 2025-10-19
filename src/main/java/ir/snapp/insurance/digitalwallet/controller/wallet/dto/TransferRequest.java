package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for transfer request
 *
 * @author Alireza Khodadoost
 */
public record TransferRequest(
        @NotNull(message = "toWalletId.is_required", groups = ValidationGroups.Presence.class)
        Long toWalletId,
        @NotNull(message = "amount.is_required", groups = ValidationGroups.Presence.class)
        @Positive(message = "amount.must_be_positive", groups = ValidationGroups.Validity.class)
        Double amount
) {
}
