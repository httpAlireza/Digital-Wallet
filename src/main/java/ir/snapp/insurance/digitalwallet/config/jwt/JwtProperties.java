package ir.snapp.insurance.digitalwallet.config.jwt;

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

    private String secret;

    private Duration expiration;
}
