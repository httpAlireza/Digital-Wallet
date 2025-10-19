package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for filtering transactions request
 *
 * @author Alireza Khodadoost
 */
@Data
public class TransactionFilterRequest {
    private LocalDateTime from = LocalDateTime.MIN;
    private LocalDateTime to = LocalDateTime.MAX;
    private int page = 0;
    private int size = 20;
}

