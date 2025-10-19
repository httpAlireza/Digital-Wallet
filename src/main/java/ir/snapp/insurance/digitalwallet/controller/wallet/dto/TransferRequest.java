package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

/**
 * DTO for transfer request
 *
 * @author Alireza Khodadoost
 */

public record TransferRequest(
        Long fromWalletId,
        Long toWalletId,
        Double amount
) {
}
