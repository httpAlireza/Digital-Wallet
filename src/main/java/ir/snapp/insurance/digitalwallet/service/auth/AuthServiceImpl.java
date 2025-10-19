package ir.snapp.insurance.digitalwallet.service.auth;

import ir.snapp.insurance.digitalwallet.controller.auth.dto.AuthResponse;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.LoginRequest;
import ir.snapp.insurance.digitalwallet.controller.auth.dto.SignupRequest;
import ir.snapp.insurance.digitalwallet.model.User;
import ir.snapp.insurance.digitalwallet.repository.UserRepository;
import ir.snapp.insurance.digitalwallet.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthService interface providing authentication and user registration functionalities.
 *
 * @author Alireza Khodadoost
 */
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
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(user.getUsername());

        return new AuthResponse(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(newUser);

        String token = jwtUtils.generateToken(newUser.getUsername());

        return new AuthResponse(token);
    }
}
