package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import ir.snapp.insurance.digitalwallet.enums.Currency;
import lombok.Data;

/**
 * DTO for wallet creation request
 *
 * @author Alireza Khodadoost
 */
@Data
public class WalletRequest {
    private String name;
    private Currency currency;
}

