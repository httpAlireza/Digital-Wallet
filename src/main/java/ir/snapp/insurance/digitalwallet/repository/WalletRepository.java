package ir.snapp.insurance.digitalwallet.repository;


import ir.snapp.insurance.digitalwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Wallet entity operations.
 *
 * @author Alireza Khodadoost
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findByUserUsername(String username);

    Optional<Wallet> findByIdAndUserUsername(Long id, String username);

    boolean existsByIdAndUserUsername(Long id, String username);
}

