package ir.snapp.insurance.digitalwallet.model;

import ir.snapp.insurance.digitalwallet.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a transaction in the digital wallet system.
 *
 * @author Alireza Khodadoost
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transaction")
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "from_wallet_id")
    private Wallet fromWallet;  // Nullable for deposit

    @ManyToOne
    @JoinColumn(name = "to_wallet_id")
    private Wallet toWallet;    // Nullable for withdraw

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}

