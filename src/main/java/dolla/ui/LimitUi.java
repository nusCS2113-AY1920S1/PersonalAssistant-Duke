package dolla.ui;

import dolla.model.Record;

//@@author Weng-Kexin
public class LimitUi extends Ui {

    private static final String INVALID_SET_FORMAT = "\tOOPS! Please follow the format "
                                                     + "'set [limitType] [AMOUNT] [DURATION]'";
    private static final String DUPLICATE_ERROR = "\tThere can only be one budget/saving for each duration "
                                                    + "(daily/weekly/monthly).";
    private static final String INVALID_SHOW_FORMAT = "\tOOPS! Please follow the format "
                                                    + "'remaining [DURATION] budget' to view your remaining budget!";
    private static final String INVALID_LIMIT_TYPE_MSG = "\tOOPS! Please specify the limit duration: "
                                                       + "daily/weekly/monthly.";

    private static final String GOOD_JOB = "\tKeep up the good work!";

    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";

    /**
     * Invalid set command printer.
     */
    public static void invalidSetCommandPrinter() {
        System.out.println(line);
        System.out.println(INVALID_SET_FORMAT);
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
        System.out.println(DUPLICATE_ERROR);
        System.out.println(MSG_MODIFY);
        System.out.println(line);
    }

    /**
     * Prints a message notifying user about a non-existing budget for the said duration.
     * @param msg      Message to send to user.
     * @param duration Duration of budget that is non-existent.
     */
    public static void noExistingBudgetPrinter(String msg, String duration) {
        System.out.println(line);
        System.out.println(msg + LEFT_BRACKET + duration + RIGHT_BRACKET);
        System.out.println(line);
    }

    /**
     * Prints a message informing user of invalid show format.
     */
    public static void invalidShowFormat() {
        System.out.println(line);
        System.out.println(INVALID_SHOW_FORMAT);
        System.out.println(line);
    }

    /**
     * Prints the remaining budget with a bar graph.
     * @param remainingBudget  Remaining budget
     * @param budget           Initial budget
     * @param expenses         Expenses of the duration
     * @param duration         Duration of the budget
     */
    public static void remainingBudgetPrinter(double remainingBudget, double budget, double expenses, String duration) {
        System.out.println(line);
        System.out.println("\tYour remaining " + duration + " budget is: $" + remainingBudget + ".");
        barGraphPrinter(budget, expenses);
        System.out.println(GOOD_JOB);
        System.out.println(line);
    }

    private static void barGraphPrinter(double budget, double expenses) {
        int ratio = (int) Math.ceil((expenses / budget) * 58);
        System.out.print("\t" + LEFT_BRACKET);
        for (int i = 0; i < ratio; i++) {
            System.out.print("x");
        }
        for (int i = ratio; i < 58; i++) {
            System.out.print(" ");
        }
        System.out.println(RIGHT_BRACKET);
    }

    /**
     * Prints exceeded budget message.
     * @param budget   Budget exceeded
     * @param duration Duration of the budget
     */
    public static void exceededBudgetPrinter(double budget, String duration) {
        System.out.println(line);
        System.out.println("\tOh no! You have exceeded your " + duration + " budget by: $" + budget + ".");
        System.out.println(line);
    }

    /**
     * Prints reached budget message.
     * @param duration Duration of the budget
     */
    public static void reachedBudgetPrinter(String duration) {
        System.out.println(line);
        System.out.println("\tOh no! You have reached your " + duration + " budget!");
        System.out.println(line);
    }

    /**
     * Prints a message between 2 lines.
     * @param message Message to be printed
     */
    public static void messagePrinter(String message) {
        System.out.println(line);
        System.out.println(message);
        System.out.println(line);
    }
}
