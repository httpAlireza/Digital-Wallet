package ir.snapp.insurance.digitalwallet.service.auth;

import ir.snapp.insurance.digitalwallet.controller.auth.dto.AuthResponse;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.ChangePasswordRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.LoginRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.SignupRequest;
import ir.snapp.insurance.digitalwallet.model.User;
import ir.snapp.insurance.digitalwallet.repository.UserRepository;
import ir.snapp.insurance.digitalwallet.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static ir.snapp.insurance.digitalwallet.exception.PredefinedError.*;

/**
 * Implementation of the AuthService interface providing authentication and user registration functionalities.
 *
 * @author Alireza Khodadoost
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(user.getUsername());

        log.debug("User {} logged in successfully", user.getUsername());
        return new AuthResponse(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw USER_ALREADY_EXISTS.getAppException();
        }

        User newUser = new User();
        newUser.setUsername(request.username());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(newUser);

        String token = jwtUtils.generateToken(newUser.getUsername());

        log.debug("User {} signed up successfully", newUser.getUsername());
        return new AuthResponse(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(USER_NOT_FOUND::getAppException);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw CURRENT_PASSWORD_INCORRECT.getAppException();
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }
}
