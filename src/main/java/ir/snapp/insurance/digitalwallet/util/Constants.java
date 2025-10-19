package ir.snapp.insurance.digitalwallet.util;

import java.time.LocalDateTime;

/**
 * Class containing application-wide constants.
 *
 * @author Alireza Khodadoost
 */
public class Constants {
    public final static String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";

    public final static LocalDateTime MIN_DATE = LocalDateTime.of(1970, 1, 1, 0, 0);
    public final static LocalDateTime MAX_DATE = LocalDateTime.of(2100, 12, 31, 23, 59);
}
