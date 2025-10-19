package ir.snapp.insurance.digitalwallet.repository;

import ir.snapp.insurance.digitalwallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for User entity operations.
 *
 * @author Alireza Khodadoost
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}

