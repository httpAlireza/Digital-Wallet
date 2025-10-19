package ir.snapp.insurance.digitalwallet.service.auth;

import ir.snapp.insurance.digitalwallet.exception.PredefinedError;
import ir.snapp.insurance.digitalwallet.model.User;
import ir.snapp.insurance.digitalwallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Custom implementation of UserDetailsService to load user-specific data.
 *
 * @author Alireza Khodadoost
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(PredefinedError.USER_NOT_FOUND::getAppException);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
