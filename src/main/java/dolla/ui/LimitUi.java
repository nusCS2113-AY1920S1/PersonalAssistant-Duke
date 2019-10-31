package dolla.ui;

/**
 * LimitUi is a class that handles all limit related user interactions.
 */
//@@author Weng-Kexin
public class LimitUi extends Ui {

    private static final String MSG_INVALID_FORMAT = "\tOOPS! Please follow the format " +
                                                     "'set [limitType] [AMOUNT] [DURATION]'";
    private static final String MSG_INVALID_AMOUNT = "\tOOPS! Please input the amount correctly!";
    private static final String MSG_INVALID_TYPE = "\tOOPS! Please specify the type of limit! (saving/budget)";
    private static final String MSG_INVALID_DURATION = "\tOOPS! Please specify the duration of your limit! " +
                                                       "(daily/weekly/monthly)";

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
     * Invalid limit type printer.
     */
    public static void invalidLimitTypePrinter() {
        System.out.println(line);
        System.out.println(MSG_INVALID_TYPE);
        System.out.println(line);
    }

    /**
     * Invalid limit duration printer.
     */
    public static void invalidLimitDurationPrinter() {
        System.out.println(line);
        System.out.println(MSG_INVALID_DURATION);
        System.out.println(line);
    }
}
