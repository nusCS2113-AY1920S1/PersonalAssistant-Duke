package dolla.ui;

public class ViewUi extends Ui {

    private static String AnsiColor;

    public static void printViewSingleExpense(double amount, String desc) {
        AnsiColor = setColor(amount);
        System.out.println("\t[" + AnsiColor +  amount + "] [" + desc + "]"
                + ANSI_RESET);
    }

    public static void printOverallExpense(double sum, String date) {
        System.out.println(AnsiColor + "\n\t Overall Expense for " + date + ": " + sum);
    }

    private static String setColor(double amount) {
        if (amount < 0) {
            return ANSI_RED;
        } else if (amount > 0) {
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
