package ir.snapp.insurance.digitalwallet.exception;

import org.springframework.http.HttpStatus;

/**
 * A general handler error to be used by handler implementations.
 *
 * @author Alireza Khodadoust
 */
public interface AppError {

    /**
     * Represents the error code.
     */
    String getErrorCode();

    /**
     * Represents the equivalent http status code for this error.
     * Mainly used to differentiate an error response from a successful response.
     */
    HttpStatus getHttpStatus();

    default AppException getAppException() {
        return new AppException(this);
    }
}
