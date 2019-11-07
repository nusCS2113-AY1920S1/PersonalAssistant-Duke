package dolla.exception;

import dolla.ui.Ui;

public class DollaException extends Exception {

    private static final String INVALID_AMOUNT_MSG = "\tOOPS! The amount you have entered is invalid.\n" +
                                                     "\tPlease key in a non-negative, " +
                                                     "non-zero value that is lesser than 1,000,000.";

    public static String invalidAmount() {
        Ui.invalidAmountPrinter(INVALID_AMOUNT_MSG);
        return INVALID_AMOUNT_MSG;
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
