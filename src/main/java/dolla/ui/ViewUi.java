package dolla.ui;

public class ViewUi extends Ui {

    private static String AnsiColor;
    private static String sign = "";
    private static double absAmount;

    public static void printViewSingleExpense(double amount, String desc) {
        AnsiColor = setColor(amount);
        absAmount = Math.abs(amount);
        System.out.println(AnsiColor + "\t[" + sign + absAmount + "] [" + desc + "]"
                + ANSI_RESET);
    }

    public static void printOverallExpense(double sum, String date) {
        System.out.println(AnsiColor + "\n\t==Overall Expense for " + date + ": " + sum
                + ANSI_RESET);
    }

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


    public static void printNoRelevantExpense(String dateStr) {
        System.out.println("I couldn't find any entries from " + dateStr
                + ", so I guess your overall expense for " + dateStr + "is 0! Hurray!");
    }
}
