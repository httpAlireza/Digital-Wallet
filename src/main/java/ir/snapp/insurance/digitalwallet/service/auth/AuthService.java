package ir.snapp.insurance.digitalwallet.service.auth;


import ir.snapp.insurance.digitalwallet.controller.auth.dto.AuthResponse;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.ChangePasswordRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.LoginRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.SignupRequest;

/**
 * Service interface for authentication operations such as login and signup.
 *
 * @author Alireza Khodadoost
 */
public interface AuthService {
    /**
     * Authenticates a user based on the provided login request.
     *
     * @param request the login request containing user credentials
     * @return an authentication response containing the authentication token
     */
    AuthResponse login(LoginRequest request);
    /**
     * Registers a new user based on the provided signup request.
     *
     * @param request the signup request containing user details
     * @return an authentication response containing the authentication token
     */
    AuthResponse signup(SignupRequest request);

    /**
     * Changes the password for the specified user.
     *
     * @param username the username of the user
     * @param request  the change password request containing current and new passwords
     */
    void changePassword(String username, ChangePasswordRequest request);

}

