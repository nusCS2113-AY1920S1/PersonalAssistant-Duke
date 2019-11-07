package dolla.ui;

import dolla.task.Record;

/**
 * LimitUi is a class that handles all limit related user interactions.
 */
//@@author Weng-Kexin
public class LimitUi extends Ui {

    private static final String MSG_INVALID_FORMAT = "\tOOPS! Please follow the format "
                                                     + "'set [limitType] [AMOUNT] [DURATION]'";
    private static final String MSG_INVALID_AMOUNT = "\tOOPS! Please input the amount correctly!";
    private static final String MSG_UNIQUE_LIMITS = "\tThere can only be one budget/saving for each duration "
                                                    + "(daily/weekly/monthly).";

    /**
     * Prints invalid amount message.
     */
    public static void invalidAmountPrinter() {
        System.out.println(line);
        System.out.println(MSG_INVALID_AMOUNT);
        System.out.println(line);
    }

    /**
     * Invalid set command printer.
     */
    public static void invalidSetCommandPrinter() {
        System.out.println(line);
        System.out.println(MSG_INVALID_FORMAT);
        System.out.println(line);
    }

    /**
     * Prints a message with the related details about an existing limit.
     * @param existingLimit The existing limit.
     */
    public static void existingLimitPrinter(Record existingLimit) {
        String limitType = existingLimit.getType();
        String limitDuration = existingLimit.getDuration();
        System.out.println(line);
        System.out.println("\tOOPS! You already have a " + limitDuration + " " + limitType + ":");
        System.out.println("\t" + existingLimit.getRecordDetail());
        System.out.println(MSG_UNIQUE_LIMITS);
        System.out.println(MSG_MODIFY);
        System.out.println(line);
    }
}
