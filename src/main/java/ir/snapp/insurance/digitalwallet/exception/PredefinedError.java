package ir.snapp.insurance.digitalwallet.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

/**
 * Encapsulates a set of predefined errors as an Enum.
 *
 * @author Alireza Khodadoust
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = OBJECT)
public enum PredefinedError implements  AppError {
    INSUFFICIENT_FUNDS("insufficient.funds", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("user.not.found", HttpStatus.NOT_FOUND),
    SERVER_ERROR("server.error", HttpStatus.INTERNAL_SERVER_ERROR),
    TARGET_WALLET_NOT_FOUND("targetWallet.not.found", HttpStatus.NOT_FOUND),
    CURRENCY_MISMATCH("currency.mismatch", HttpStatus.BAD_REQUEST),
    WALLET_NOT_FOUND("wallet.not.found", HttpStatus.NOT_FOUND),
    WALLET_ALREADY_EXISTS("wallet.already.exists", HttpStatus.BAD_REQUEST),
    ;

    private final String errorCode;

    private final HttpStatus httpStatus;
}
