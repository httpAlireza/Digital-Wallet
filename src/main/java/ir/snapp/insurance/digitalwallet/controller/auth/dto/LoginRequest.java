package ir.snapp.insurance.digitalwallet.controller.auth.dto;

/**
 * DTO for login request
 *
 * @author Alireza Khodadoost
 */

public record LoginRequest(
        String username,
        String password
) {
}

