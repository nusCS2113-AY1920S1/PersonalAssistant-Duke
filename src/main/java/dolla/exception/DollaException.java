package dolla.exception;

import dolla.ui.LimitUi;

//@@author Weng-Kexin
public class DollaException extends Exception {

    private static final String INVALID_AMOUNT_MSG = "\tOOPS! The amount you have entered is invalid.\n"
                                                     + "\tPlease key in a non-negative, "
                                                     + "non-zero value that is lesser than 1,000,000.";

    private static final String NO_BUDGET_MSG = "\tOOPS! There is no budget set for the duration you specified: ";

    private static final String INVALID_LIMIT_TYPE_MSG = "\tOOPS! Please specify the limit type: budget/saving.";

    private static final String INVALID_LIMIT_DURATION_MSG = "\tOOPS! Please specify the limit duration: "
                                                           + "daily/weekly/monthly.";

    //private static final String INVALID_COMMAND_MSG = "\tOOPS! The command is invalid. Please enter a valid command!";

    /**
     * Prints a non existing budget error.
     * @return Exception message
     */
    public static String noExistingBudget(String duration) {
        LimitUi.noExistingBudgetPrinter(NO_BUDGET_MSG, duration);
        return NO_BUDGET_MSG + duration;
    }

    /**
     * Prints an invalid amount error.
     * @return Exception message
     */
    public static String invalidAmount() {
        LimitUi.invalidAmountPrinter(INVALID_AMOUNT_MSG);
        return INVALID_AMOUNT_MSG;
    }

    /**
     * Prints an invalid limit type error.
     * @return Exception message
     */
    public static String invalidLimitType() {
        LimitUi.messagePrinter(INVALID_LIMIT_TYPE_MSG);
        return INVALID_LIMIT_TYPE_MSG;
    }

    /**
     * Prints an invalid limit duration error.
     * @return Exception message
     */
    public static String invalidLimitDuration() {
        LimitUi.messagePrinter(INVALID_LIMIT_DURATION_MSG);
        return INVALID_LIMIT_DURATION_MSG;
    }

    /*
    public static String invalidInput() {
        Ui.printInvalidCommandError();
        return INVALID_COMMAND_MSG;
    }
     */


    /**
     * Creates a new DollaException object.
     *
     * @param message The exception message.
     */
    public DollaException(String message) {
        super(message);
    }
}
