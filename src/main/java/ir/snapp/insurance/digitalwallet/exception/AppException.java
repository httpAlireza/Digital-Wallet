

package ir.snapp.insurance.digitalwallet.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * A general application exception to be used by application modules.
 *
 * @author Alireza Khodadoust
 */
@Getter
@ToString
public class AppException extends RuntimeException {
    private final AppError error;

    public AppException(AppError error) {
        super(error.toString());
        this.error = error;
    }
}
