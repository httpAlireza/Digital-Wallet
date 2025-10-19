package ir.snapp.insurance.digitalwallet.controller.auth.dto;

import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static ir.snapp.insurance.digitalwallet.util.Constants.PASSWORD_PATTERN;

/**
 * DTO for change password request
 *
 * @author Alireza Khodadoost
 */
public record ChangePasswordRequest(
        @NotBlank(message = "currentPassword.is_required")
        String currentPassword,

        @NotBlank(message = "newPassword.is_required")
        @Pattern(
                regexp = PASSWORD_PATTERN,
                message = "password.must_meet_complexity_requirements",
                groups = ValidationGroups.Validity.class
        )
        String newPassword
) {
}
