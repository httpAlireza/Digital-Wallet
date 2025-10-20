package ir.snapp.insurance.digitalwallet.config.jwt;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/**
 * Encapsulates the configuration properties for JWT.
 *
 * @author Alireza Khodadoust
 */
@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @NotNull(message = "jwt.secret.is_required")
    private String secret;

    @NotNull(message = "jwt.expiration.is_required")
    private Duration expiration;
}
