package dolla.ui;

/**
 * LimitUi is a class that handles all limit related user interactions.
 */
//@@author Weng-Kexin
public class LimitUi extends Ui {

    /**
     * Prints remove limit message.
     *
     * @param type     the type of limit
     * @param duration the duration
     */
    public static void echoRemoveLimit(String type, String duration) {
        System.out.println(line);
        System.out.println("\tGot it. I've removed this Limit: ");
        System.out.println("\t" + duration + " " + type); //todo: change to the limit that is removed
        System.out.println(line);
    }

    /**
     * Prints invalid amount message.
     */
    public static void invalidAmountPrinter() {
        System.out.println(line);
        System.out.println("\tOOPS! Please input the amount correctly!");
        System.out.println(line);
    }

    /**
     * Invalid set command printer.
     */
    public static void invalidSetCommandPrinter() {
        System.out.println(line);
        System.out.println("\tOOPS! Please follow the format 'set [limitType] [AMOUNT] [DURATION]'");
        System.out.println(line);
    }

    /**
     * Invalid limit type printer.
     */
    public static void invalidLimitTypePrinter() {
        System.out.println(line);
        System.out.println("\tOOPS! Please specify the type of limit you want to set!");
        System.out.println(line);
    }

    /**
     * Invalid limit duration printer.
     */
    public static void invalidLimitDurationPrinter() {
        System.out.println(line);
        System.out.println("\tOOPS! Please specify the duration you want to set to your limit!");
        System.out.println(line);
    }
}
