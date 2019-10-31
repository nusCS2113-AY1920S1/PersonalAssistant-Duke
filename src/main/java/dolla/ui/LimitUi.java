package dolla.ui;

/**
 * LimitUi is a class that handles all limit related user interactions.
 */
//@@author Weng-Kexin
public class LimitUi extends Ui {

    public static final String MSG_INVALID_COMMAND_FORMAT = "\tOOPS! Please follow the format 'set [limitType] [AMOUNT] [DURATION]'";
    public static final String MSG_INVALID_LIMIT_AMOUNT = "\tOOPS! Please input the amount correctly!";
    public static final String MSG_INVALID_LIMIT_TYPE = "\tOOPS! Please specify the type of limit! (saving/budget)";
    public static final String MSG_INVALID_LIMIT_DURATION = "\tOOPS! Please specify the duration of your limit! (daily/weekly/monthly)";
    public static final String MSG_LIMIT_DOES_NOT_EXIST = "\tOOPS! The limit you want to remove does not exist!";

    /**
     * Prints invalid amount message.
     */
    public static void invalidAmountPrinter() {
        System.out.println(line);
        System.out.println(MSG_INVALID_LIMIT_AMOUNT);
        System.out.println(line);
    }

    /**
     * Invalid set command printer.
     */
    public static void invalidSetCommandPrinter() {
        System.out.println(line);
        System.out.println(MSG_INVALID_COMMAND_FORMAT);
        System.out.println(line);
    }

    /**
     * Invalid limit type printer.
     */
    public static void invalidLimitTypePrinter() {
        System.out.println(line);
        System.out.println(MSG_INVALID_LIMIT_TYPE);
        System.out.println(line);
    }

    /**
     * Invalid limit duration printer.
     */
    public static void invalidLimitDurationPrinter() {
        System.out.println(line);
        System.out.println(MSG_INVALID_LIMIT_DURATION);
        System.out.println(line);
    }

    /**
     * Prints message to inform user that limit does not exist.
     */
    public static void limitNotFoundPrinter() {
        System.out.println(line);
        System.out.println(MSG_LIMIT_DOES_NOT_EXIST);
        System.out.println(line);
    }
}
