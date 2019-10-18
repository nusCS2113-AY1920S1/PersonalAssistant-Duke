package wallet.ui;

import wallet.model.record.Expense;
import wallet.thread.ChartThread;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    /**
     * Scanner object used for reading input from user.
     */
    private Scanner sc;

    /**
     * Constructs a new ui.Ui object.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints the welcome message of the program.
     */
    public void welcomeMsg() {
        String logo = "__             __       _   _         \n"
                + "\\ \\    __     / /      | | | |        _\n"
                + " \\ \\  /  \\   / /___,__ | | | | ______| |_    \n"
                + "  \\ \\/ /\\  \\/ /  [] | || | | |  []_\\_  __|\n"
                + "   \\__/  \\___/ \\__,_|_||_| |_|\\___/  |_|\n";
        System.out.println("Hello from\n" + logo);

        printLine();
        System.out.println("Hello! Welcome to the WalletCLi Application!");
        System.out.println("What can I do for you?");
        printLine();
    }

    /**
     * Prints the goodbye message when the user exits the program.
     */
    public void byeMsg() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prompts the user for input.
     *
     * @return The input of the user.
     */
    public String readLine() {
        return sc.nextLine();
    }

    /**
     * Prints the separator lines for UI.
     */
    public void printLine() {
        System.out.println("_______________________________________________________________________________");
    }

    /**
     * Displays the expense list in table format.
     */
    public static void printExpenseTable(ArrayList<Expense> expenseList) {
        System.out.println("Here are the expenses in your list:");
        System.out.println("-----------------------------------------------------"
                + "-----------------------------------------------\n"
                + "|  ID  |              Description                 |"
                + " Category |    Date    |   Amount   | Recurring |\n"
                + "|-------------------------------------------------------------"
                + "-------------------------------------|");
        double total = 0;
        for (Expense e : expenseList) {
            if (e.isRecurring()) {
                System.out.printf("| %-4d | %-40s | %-8s | %-10s |  $%-7.2f  |  %-7s  |\n", e.getId(),
                        e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), e.getRecFrequency());
            } else {
                System.out.printf("| %-4d | %-40s | %-8s | %-10s |  $%-7.2f  |  %-7s  |\n",
                        e.getId(), e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), "No");

            }
            total += e.getAmount();
        }
        System.out.println("-----------------------------------------------------"
                + "-----------------------------------------------");
        System.out.println("Total amount spent: $" + total);
    }

    public void drawPieChart() {
        ChartThread chartThread = new ChartThread();
        System.out.println("Please wait while we draw the pie chart...");
    }
}
