package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import lombok.Data;

/**
 * DTO for transfer request
 *
 * @author Alireza Khodadoost
 */
@Data
public class TransferRequest {
    private Long fromWalletId;
    private Long toWalletId;
    private Double amount;
}

