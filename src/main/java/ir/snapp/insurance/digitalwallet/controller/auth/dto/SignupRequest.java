package ir.snapp.insurance.digitalwallet.controller.auth.dto;

import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static ir.snapp.insurance.digitalwallet.util.Constants.PASSWORD_PATTERN;

/**
 * DTO for signup request
 *
 * @author Alireza Khodadoost
 */
public record SignupRequest(
        @NotBlank(message = "username.is_required", groups = ValidationGroups.Presence.class)
        String username,
        @NotBlank(message = "password.is_required", groups = ValidationGroups.Presence.class)
        @Pattern(
                regexp = PASSWORD_PATTERN,
                message = "password.must_meet_complexity_requirements",
                groups = ValidationGroups.Validity.class
        )
        String password
) {
}
