package ir.snapp.insurance.digitalwallet.controller.wallet.dto;

import ir.snapp.insurance.digitalwallet.util.ValidationGroups;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

import static ir.snapp.insurance.digitalwallet.util.Constants.MAX_DATE;
import static ir.snapp.insurance.digitalwallet.util.Constants.MIN_DATE;

/**
 * DTO for filtering transactions request
 *
 * @author Alireza Khodadoost
 */
@Data
public class TransactionFilterCriteria {

    private LocalDateTime from = MIN_DATE;

    private LocalDateTime to = MAX_DATE;

    @Min(message = "page.cannot_be_negative", value = 0, groups = ValidationGroups.Validity.class)
    private int page = 0;

    @Positive(message = "size.must_be_positive", groups = ValidationGroups.Validity.class)
    private int size = 20;

    @AssertTrue(message = "filter.date_range_invalid", groups = ValidationGroups.Late.class)
    private boolean isDateRangeValid() {
        return from.isBefore(to);
    }
}

