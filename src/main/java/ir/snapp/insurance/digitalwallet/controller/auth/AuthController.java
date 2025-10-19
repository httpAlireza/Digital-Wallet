package ir.snapp.insurance.digitalwallet.controller.auth;

import ir.snapp.insurance.digitalwallet.controller.auth.dto.AuthResponse;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.LoginRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.SignupRequest;
import ir.snapp.insurance.digitalwallet.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication operations such as login and signup.
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
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
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
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}

