package duke.ui;

import duke.model.Expense;
import duke.model.ExpenseList;
import duke.exception.DukeException;


import java.util.Scanner;

/**
 * Represents the User Interface of Duke, and
 * manages both input and output operations.
 */
public class TextUi {
    private Scanner dukeIn;
    private String mostRecent;

    /**
     * Constructs an Ui object.
     */
    public TextUi() {
        dukeIn = new Scanner(System.in);
    }

    /**
     * Shows welcome message to the user when Duke starts.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("I am Duke. What can I do for you?");
    }

    /**
     * Reads one line of user's commands.
     *
     * @return User's command in {@code String} type.
     */
    public String readCommand() {
        return dukeIn.nextLine();
    }

    /**
     * Replaces the {@code System.out.println} method.
     *
     * @param s The string to be printed.
     */
    public void println(String s) {
        System.out.println(s);
        mostRecent = s;
    }

    /**
     * Prints the message of the exception.
     *
     * @param e the {@code DukeException} whose message will be printed.
     */
    public void showError(DukeException e) {
        System.out.println(e.getMessage());
        mostRecent = e.getMessage();
    }

    /**
     * Prints the {@code ExpenseList} given.
     *
     * @param expenseList {@code ExpenseList} that we want to be printed
     */
    public void printExpenseList(ExpenseList expenseList) {
        if (expenseList.internalSize() > 0) {
            int count = 1;
            for (Expense expense : expenseList.getExternalList()) {
                println(expense.toString());
                count++;
            }
        }
    }

    public String getMostRecent() {
        return mostRecent;
    }


}
