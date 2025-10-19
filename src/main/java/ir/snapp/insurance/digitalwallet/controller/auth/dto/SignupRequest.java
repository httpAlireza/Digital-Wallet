package ir.snapp.insurance.digitalwallet.controller.auth.dto;

/**
 * DTO for signup request
 *
 * @author Alireza Khodadoost
 */
public record SignupRequest(
        String username,
        String password
) {
}
