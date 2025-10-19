package ir.snapp.insurance.digitalwallet.controller.auth.dto;

import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for login request
 *
 * @author Alireza Khodadoost
 */

public record LoginRequest(
        @NotBlank(message = "username.is_required", groups = ValidationGroups.Presence.class)
        String username,
        @NotBlank(message = "password.is_required", groups = ValidationGroups.Presence.class)
        String password
) {
}

