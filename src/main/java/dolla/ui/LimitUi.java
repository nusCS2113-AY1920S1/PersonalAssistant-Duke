package dolla.ui;

import dolla.model.Record;
import dolla.parser.ParserStringList;

import static dolla.parser.ParserStringList.SPACE;

//@@author Weng-Kexin
public class LimitUi extends Ui {

    private static final String INVALID_FORMAT_MSG = "\tOOPS! Please follow the format:";
    private static final String SET_FORMAT_MSG = "\t'set [limitType] [AMOUNT] [DURATION]' to set a new limit.";
    private static final String SHOW_FORMAT_MSG = "\t'remaining [DURATION] [limitType]' to view your limit goals!";
    private static final String INVALID_LIMIT_TYPE_MSG = "\tOOPS! Please specify the limit type: budget/saving.";
    private static final String INVALID_LIMIT_DURATION_MSG = "\tOOPS! Please specify the limit duration: "
                                                           + "daily/weekly/monthly.";
    private static final String DUPLICATE_LIMITS_MSG = "\tOOPS! There can only be one budget/saving for each duration.";
    private static final String EXISTING_LIMIT_MSG = "\tOOPS! You already have a ";

    private static final String NO_BUDGET_MSG = "\tOOPS! There is no budget set for the duration you specified";
    private static final String NO_SAVING_MSG = "\tOOPS! There is no saving set for the duration you specified";

    private static final String REACHED_DS_MSG = "\tCongratulations! You have reached your daily saving goal!";
    private static final String REACHED_WS_MSG = "\tCongratulations! You have reached your weekly saving goal!";
    private static final String REACHED_MS_MSG = "\tCongratulations! You have reached your monthly saving goal!";
    private static final String WELL_DONE_MSG = "\tKeep up the good work!";

    private static final String REACHED_DB_MSG = "\tOh no! You have reached your daily budget. Time to cut down!";
    private static final String REACHED_WB_MSG = "\tOh no! You have reached your weekly budget. Time to cut down!";
    private static final String REACHED_MB_MSG = "\tOh no! You have reached your monthly budget. Time to cut down!";

    private static final String NO_ACHIEVED_SAVINGS_MSG = "\tOh no! You have not saved any money. Work harder!";

    private static final String EXCEEDED_DB_MSG = "\tOh no! You have exceeded your daily budget by: $";
    private static final String EXCEEDED_WB_MSG = "\tOh no! You have exceeded your weekly budget by: $";
    private static final String EXCEEDED_MB_MSG = "\tOh no! You have exceeded your monthly budget by: $";

    private static final String EXCEEDED_DS_MSG = "\tCongratulations! You have exceeded your daily saving goal by $";
    private static final String EXCEEDED_WS_MSG = "\tCongratulations! You have exceeded your weekly saving goal by $";
    private static final String EXCEEDED_MS_MSG = "\tCongratulations! You have exceeded your monthly saving goal by $";

    private static final String REMAINING_AMOUNT_MSG = "\tYou still have $";
    private static final String REMAINING_DS_MSG = " more to go before you reach your daily saving goal. Keep it up!";
    private static final String REMAINING_WS_MSG = " more to go before you reach your weekly saving goal. Keep it up!";
    private static final String REMAINING_MS_MSG = " more to go before you reach your monthly saving goal. Keep it up!";

    private static final String REMAINING_DB_MSG = "\tYour remaining daily budget is: $";
    private static final String REMAINING_WB_MSG = "\tYour remaining weekly budget is: $";
    private static final String REMAINING_MB_MSG = "\tYour remaining monthly budget is: $";

    private static final String VISUAL_REPRESENTATION_MSG = "\tHere is a visual representation of your current goal:";

    private static final String X = "x";
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";

    /**
     * Invalid limitType Printer.
     */
    public static void invalidLimitTypePrinter() {
        System.out.println(line);
        System.out.println(INVALID_LIMIT_TYPE_MSG);
        System.out.println(line);
    }

    /**
     * Invalid Limit Duration Printer.
     */
    public static void invalidLimitDurationPrinter() {
        System.out.println(line);
        System.out.println(INVALID_LIMIT_DURATION_MSG);
        System.out.println(line);
    }

    /**
     * Invalid set command printer.
     */
    public static void invalidSetCommandPrinter() {
        System.out.println(line);
        System.out.println(INVALID_FORMAT_MSG);
        System.out.println(SET_FORMAT_MSG);
        System.out.println(line);
    }

    /**
     * Invalid Show Remaining Limit Command Printer.
     */
    public static void invalidShowRemainingLimitPrinter() {
        System.out.println(line);
        System.out.println(INVALID_FORMAT_MSG);
        System.out.println(SHOW_FORMAT_MSG);
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
        System.out.println(EXISTING_LIMIT_MSG + limitDuration + SPACE + limitType);
        System.out.println("\t" + existingLimit.getRecordDetail());
        System.out.println(DUPLICATE_LIMITS_MSG);
        System.out.println(MSG_MODIFY);
        System.out.println(line);
    }

    /**
     * Prints a message notifying user about a non-existing limit for the specified duration.
     * @param type      Type of limit
     */
    public static void noExistingLimitPrinter(String type) {
        System.out.println(line);
        if (type.equals(ParserStringList.LIMIT_TYPE_B)) {
            System.out.println(NO_BUDGET_MSG);
        } else {
            System.out.println(NO_SAVING_MSG);
        }
        System.out.println(line);
    }

    /**
     * Prints exceeded budget message.
     * @param budget   Budget exceeded
     * @param duration Duration of the budget
     */
    public static void exceededBudgetPrinter(double budget, String duration) {
        System.out.println(line);
        if (duration.equals(ParserStringList.LIMIT_DURATION_D)) {
            System.out.println(EXCEEDED_DB_MSG + budget);
        } else if (duration.equals(ParserStringList.LIMIT_DURATION_W)) {
            System.out.println(EXCEEDED_WB_MSG + budget);
        } else {
            System.out.println(EXCEEDED_MB_MSG + budget);
        }
        System.out.println(line);
    }

    /**
     * Prints reached budget message.
     * @param duration Duration of the budget
     */
    public static void reachedBudgetPrinter(String duration) {
        System.out.println(line);
        if (duration.equals(ParserStringList.LIMIT_DURATION_D)) {
            System.out.println(REACHED_DB_MSG);
        } else if (duration.equals(ParserStringList.LIMIT_DURATION_W)) {
            System.out.println(REACHED_WB_MSG);
        } else {
            System.out.println(REACHED_MB_MSG);
        }
        System.out.println(line);
    }

    /**
     * Prints out message informing user of no savings reached.
     */
    public static void noSavingsPrinter() {
        System.out.println(line);
        System.out.println(NO_ACHIEVED_SAVINGS_MSG);
        System.out.println(line);
    }

    /**
     * Prints message informing user of their achieved saving goal for the duration specified.
     * @param duration The duration in which they have achieved their saving goal.
     */
    public static void reachedSavingPrinter(String duration) {
        System.out.println(line);
        if (duration.equals(ParserStringList.LIMIT_DURATION_D)) {
            System.out.println(REACHED_DS_MSG);
        } else if (duration.equals(ParserStringList.LIMIT_DURATION_W)) {
            System.out.println(REACHED_WS_MSG);
        } else {
            System.out.println(REACHED_MS_MSG);
        }
        System.out.println(WELL_DONE_MSG);
        System.out.println(line);
    }

    /**
     * Prints message informing user of exceeded saving goal of the input amount for the given duration.
     * @param duration Duration of the saving goal.
     * @param amount   Amount exceeded.
     */
    public static void exceededSavingPrinter(String duration, double amount) {
        System.out.println(line);
        if (duration.equals(ParserStringList.LIMIT_DURATION_D)) {
            System.out.println(EXCEEDED_DS_MSG + amount);
        } else if (duration.equals(ParserStringList.LIMIT_DURATION_W)) {
            System.out.println(EXCEEDED_WS_MSG + amount);
        } else {
            System.out.println(EXCEEDED_MS_MSG + amount);
        }
        System.out.println(WELL_DONE_MSG);
        System.out.println(line);
    }

    /**
     * Prints a graphical representation of the achieved limit goal.
     * @param total       Total limit goal.
     * @param remaining   Remaining limit amount to reach/cut down.
     */
    private static void barGraphPrinter(double total, double remaining) {
        int ratio = (int) Math.ceil((remaining / total) * 58);
        System.out.println(VISUAL_REPRESENTATION_MSG);
        System.out.print("\t" + LEFT_BRACKET);
        for (int i = 0; i < ratio; i++) {
            System.out.print(X);
        }
        for (int i = ratio; i < 58; i++) {
            System.out.print(SPACE);
        }
        System.out.println(RIGHT_BRACKET);
    }

    /**
     * Prints the remaining budget with a bar graph representation.
     * @param remainingBudget  Remaining budget
     * @param budget           Initial budget
     * @param expenses         Expenses of the duration
     * @param duration         Duration of the budget
     */
    public static void remainingBudgetPrinter(double remainingBudget, double budget, double expenses, String duration) {
        System.out.println(line);
        if (duration.equals(ParserStringList.LIMIT_DURATION_D)) {
            System.out.println(REMAINING_DB_MSG + remainingBudget);
        } else if (duration.equals(ParserStringList.LIMIT_DURATION_W)) {
            System.out.println(REMAINING_WB_MSG + remainingBudget);
        } else {
            System.out.println(REMAINING_MB_MSG + remainingBudget);
        }
        barGraphPrinter(budget, expenses);
        System.out.println(WELL_DONE_MSG);
        System.out.println(line);
    }

    /**
     * Prints the remaining saving goal with a bar graph representation.
     * @param total            Total saving set
     * @param remaining        Remaining saving to achieve
     * @param duration         Duration of the saving
     */
    public static void remainingSavingPrinter(double total, double remaining, String duration) {
        System.out.println(line);
        if (duration.equals(ParserStringList.LIMIT_DURATION_D)) {
            System.out.println(REMAINING_AMOUNT_MSG + remaining + REMAINING_DS_MSG);
        } else if (duration.equals(ParserStringList.LIMIT_DURATION_W)) {
            System.out.println(REMAINING_AMOUNT_MSG + remaining + REMAINING_WS_MSG);
        } else {
            System.out.println(REMAINING_AMOUNT_MSG + remaining + REMAINING_MS_MSG);
        }
        barGraphPrinter(total, remaining);
        System.out.println(WELL_DONE_MSG);
        System.out.println(line);
    }
}
