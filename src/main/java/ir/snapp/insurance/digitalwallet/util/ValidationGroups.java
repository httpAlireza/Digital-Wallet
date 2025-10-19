package ir.snapp.insurance.digitalwallet.util;

import jakarta.validation.GroupSequence;

/**
 * Validation groups for different validation phases
 *
 * @author Alireza Khodadoost
 */
public interface ValidationGroups {
    /**
     * Presence validation group. it checks for the presence of required fields.
     * this group is validated first.
     */
    interface Presence {
    }

    /**
     * Validity validation group. it checks for the validity of field values.
     * this group is validated after presence group.
     */
    interface Validity {
    }

    /**
     * Late validation group. it checks for conditions that must be validated later.
     * this group is validated last.
     */
    interface Late {
    }

    @GroupSequence({Presence.class, Validity.class, Late.class})
    interface ValidationSeq {
    }
}