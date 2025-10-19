package ir.snapp.insurance.digitalwallet.controller.auth.dto;

import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ir.snapp.insurance.digitalwallet.util.Constants.PASSWORD_PATTERN;

/**
 * DTO for change password request
 *
 * @author Alireza Khodadoost
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "currentPassword.is_required")
    private String currentPassword;

    @NotBlank(message = "newPassword.is_required")
    @Pattern(
            regexp = PASSWORD_PATTERN,
            message = "password.must_meet_complexity_requirements",
            groups = ValidationGroups.Validity.class
    )
    private String newPassword;
}
