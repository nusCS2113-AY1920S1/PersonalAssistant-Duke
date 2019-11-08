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
        System.out.println(line);
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
        System.out.println("\tI couldn't find any entries from " + dateStr
                + ", so I guess your overall expense for " + dateStr + "is 0! Hurray!");
        System.out.println(line);
    }

    public static void printInvalidViewFormatError() {
        System.out.println(line);
        System.out.println("\tPlease follow the format for view!");
        System.out.println("\tview today");
        System.out.println("\tor");
        System.out.println("\tview [DATE]");
        System.out.println(line);
    }

    public static void printInvalidViewDateError() {
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
