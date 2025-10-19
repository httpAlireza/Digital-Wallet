package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import ir.snapp.insurance.digitalwallet.enums.Currency;
import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Arrays;

/**
 * DTO for wallet creation request
 *
 * @author Alireza Khodadoost
 */
@Data
public class WalletCreationRequest {

    @NotBlank(message = "name.is_required", groups = ValidationGroups.Presence.class)
    private String name;

    @NotBlank(message = "currency.is_required", groups = ValidationGroups.Presence.class)
    private String currency;

    @AssertTrue(message = "currency.not_supported", groups = ValidationGroups.Late.class)
    private boolean isCurrencyValid() {
        return Arrays.stream(Currency.values())
                .map(Enum::toString)
                .anyMatch(currency -> currency.equalsIgnoreCase(this.currency));
    }

    public Currency getCurrency() {
        return Currency.valueOf(currency.toUpperCase());
    }
}

