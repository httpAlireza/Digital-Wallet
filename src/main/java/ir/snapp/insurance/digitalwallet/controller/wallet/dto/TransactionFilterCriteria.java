package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for filtering transactions request
 *
 * @author Alireza Khodadoost
 */
@Data
public class TransactionFilterCriteria {

    private LocalDateTime from = LocalDateTime.of(1970, 1, 1, 0, 0);

    private LocalDateTime to = LocalDateTime.of(2100, 12, 31, 23, 59);

    @Min(message = "page.cannot_be_negative", value = 0, groups = ValidationGroups.Validity.class)
    private int page = 0;

    @Positive(message = "size.must_be_positive", groups = ValidationGroups.Validity.class)
    private int size = 20;

    @AssertTrue(message = "filter.date_range_invalid", groups = ValidationGroups.Late.class)
    private boolean isDateRangeValid() {
        return from.isBefore(to);
    }
}

