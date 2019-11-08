package dolla.ui;

public class ViewUi extends Ui {

    private static String AnsiColor;
    private static String sign = "";
    private static double absAmount;

    /**
     * Prints out a line of entry that matches the give date. ie "[+12] [Description]"
     * @param amount of money to be displayed
     * @param desc description of entry
     */
    public static void printViewSingleExpense(double amount, String desc) {
        AnsiColor = setColor(amount);
        absAmount = Math.abs(amount);
        System.out.println(AnsiColor + "\t[" + sign + absAmount + "] [" + desc + "]"
                + ANSI_RESET);
    }

    /**
     * Prints out the overall sum of the expenses and incomes for the view date.
     * @param sum overall sum of expenses and income
     * @param date String version of the view date.
     */
    public static void printOverallExpense(double sum, String date) {
        System.out.println(AnsiColor + "\n\t==Overall Expense for " + date + ": " + sum
                + ANSI_RESET);
        System.out.println(line);
    }

    /**
     * Returns the ANSI color to be used depending on whether amount is positive or negative.
     * Also sets the sign.
     * @param amount of money of the entry.
     * @return ANSI color corresponding to whether amount is positive or negative.
     */
    private static String setColor(double amount) {
        if (amount < 0) {
            sign = "-";
            return ANSI_RED;
        } else if (amount > 0) {
            sign = "+";
            return ANSI_GREEN;
        } else {
            return ANSI_RESET;
        }
    }

    /**
     * Prints a message to alert user that there are no entries with the requested date.
     * @param dateStr String related to the date to be printed
     */
    public static void printNoRelevantExpense(String dateStr) {
        System.out.println("\tI couldn't find any entries from " + dateStr
                + ", so I guess your overall expense for " + dateStr + "is 0! Hurray!");
        System.out.println(line);
    }

    /**
     * Prints out a reminder on how to properly use the view command.
     */
    public static void printInvalidViewFormatError() {
        System.out.println(line);
        System.out.println("\tPlease follow the format for view!");
        System.out.println("\tview today");
        System.out.println("\tor");
        System.out.println("\tview [DATE]");
        System.out.println(line);
    }
    
    public static void printStartLine() {
        System.out.println(line);
    }
}
