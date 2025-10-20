package ir.snapp.insurance.digitalwallet.controller.auth;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.AuthResponse;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.ChangePasswordRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.LoginRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.SignupRequest;
import ir.snapp.insurance.digitalwallet.service.auth.AuthService;
import ir.snapp.insurance.digitalwallet.util.ValidationGroups.ValidationSeq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * REST controller for user authentication and authorization.
 * <p>
 * Supports user registration, login, and password change operations.
 * JWT is used for stateless authentication; logout endpoint is not required.
 * </p>
 * Endpoints:
 * <ul>
 *     <li>POST /v1/auth/signup - Register a new user</li>
 *     <li>POST /v1/auth/login - Authenticate and get JWT token</li>
 *     <li>PATCH /v1/auth/change-password - Change password for authenticated user</li>
 * </ul>
 *
 * @author Alireza Khodadoost
 */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user and returns an authentication token.
     *
     * @param request the signup request containing user details (username, password, etc.)
     * @return {@code ResponseEntity} with {@link AuthResponse} and HTTP status 201 (Created) on success
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Validated(ValidationSeq.class) @RequestBody SignupRequest request) {
        AuthResponse response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param request the login request containing username/email and password
     * @return {@code ResponseEntity} with {@link AuthResponse} and HTTP status 200 (OK) on success
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Validated(ValidationSeq.class) @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Changes the password of the authenticated user.
     *
     * @param request   the change password request containing old and new passwords
     * @param principal the authenticated user's principal
     * @return {@code ResponseEntity} with HTTP status 204 (No Content) on success
     */
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Validated @RequestBody ChangePasswordRequest request,
            Principal principal) {
        authService.changePassword(principal.getName(), request);
        return ResponseEntity.noContent().build();
    }
}
