package ir.snapp.insurance.digitalwallet.controller.auth;

import ir.snapp.insurance.digitalwallet.controller.auth.dto.AuthResponse;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.ChangePasswordRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.LoginRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.SignupRequest;
import ir.snapp.insurance.digitalwallet.service.auth.AuthService;
import ir.snapp.insurance.digitalwallet.util.ValidationGroups.ValidationSeq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * REST controller for authentication operations such as login and signup.
 * Since we are using JWT for authentication, there is no need for logout endpoint.
 *
 * @author Alireza Khodadoost
 */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Handles user signup requests.
     *
     * @param request the signup request containing user details
     * @return a response entity containing the authentication response
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Validated(ValidationSeq.class) @RequestBody SignupRequest request) {
        AuthResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Handles user login requests.
     *
     * @param request the login request containing user credentials
     * @return a response entity containing the authentication response
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Validated(ValidationSeq.class) @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Validated @RequestBody ChangePasswordRequest request,
            Principal principal) {
        authService.changePassword(principal.getName(), request);
        return ResponseEntity.ok("Password changed successfully");
    }
}
