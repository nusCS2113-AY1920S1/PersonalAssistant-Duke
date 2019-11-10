package dolla.exception;

import dolla.ui.LimitUi;

//@@author Weng-Kexin
public class DollaException extends Exception {

    private static final String INVALID_AMOUNT_MSG = "\tOOPS! The amount you have entered is invalid.";
    private static final String NO_LIMIT_MSG = "\tOOPS! There is no limit set for the duration you specified.";
    private static final String INVALID_LIMIT_TYPE_MSG = "\tOOPS! Please specify the limit type: budget/saving.";
    private static final String INVALID_LIMIT_DURATION_MSG = "\tOOPS! Please specify the limit duration.";
    private static final String INVALID_TYPE_MSG = "OOPS! The type you have input is invalid.";

    /**
     * Prints a non existing limit error.
     * @return Exception message
     */
    public static String noExistingLimit(String type) {
        LimitUi.noExistingLimitPrinter(type);
        return NO_LIMIT_MSG;
    }

    /**
     * Prints an invalid amount error.
     * @return Exception message
     */
    public static String invalidAmount() {
        LimitUi.invalidAmountPrinter();
        return INVALID_AMOUNT_MSG;
    }

    /**
     * Prints an invalid limit type error.
     * @return Exception message
     */
    public static String invalidLimitType() {
        LimitUi.invalidLimitTypePrinter();
        return INVALID_LIMIT_TYPE_MSG;
    }

    /**
     * Prints an invalid limit duration error.
     * @return Exception message
     */
    public static String invalidLimitDuration() {
        LimitUi.invalidLimitDurationPrinter();
        return INVALID_LIMIT_DURATION_MSG;
    }

    /**
     * Returns an invalid type exception.
     * @return
     */
    public static String invalidType() {
        return INVALID_TYPE_MSG;
    }

    /**
     * Creates a new DollaException object.
     *
     * @param message The exception message.
     */
    public DollaException(String message) {
        super(message);
    }
}