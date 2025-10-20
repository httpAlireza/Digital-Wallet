package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

/**
 * DTO for balance information
 *
 * @author Alireza Khodadoost
 */
public record BalanceDto(
        Long walletId,
        Double balance
) {
}
