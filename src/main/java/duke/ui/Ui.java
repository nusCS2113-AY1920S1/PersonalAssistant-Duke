package duke.ui;

import duke.Duke;
import java.util.Scanner;

/**
 * Represents the user interaction, used for getting the user input and printing the output on the screen.
 */
public class Ui {

    private Scanner scanner;
    private static final String line = "____________________________________________________________";

    /**
     * The constructor method for Ui.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns the input entered by the user.
     * @return String the input entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Show a line.
     */
    public void showLine() {
        System.out.println("\t " + line);
    }

    /**
     * Used to print the greeting message from {@link Duke}.
     */
    public void showWelcome() {
        showLine();
        System.out.println("\t Hello! I'm Duke");
        System.out.println("\t What can I do for you?");
        showLine();
    }

    /**
     * Show loading error.
     */
    public void showLoadingError() {
        System.out.println("\t ☹ OOPS!!! Error while loading the list from the hard disc");
    }

    /**
     * Show the error to user.
     * @param e an error
     */
    public void showError(String e) {
        System.out.println(e);
    }

    /**
     * Show the task to user.
     * @param task string
     */
    public void showTask(String task) {
        System.out.println(task);
    }

    /**
     * Show that this task is marked.
     * @param doneTask The task that is marked as done
     */
    public void showMarkDone(String doneTask) {
        System.out.println("\t Nice! I've marked this task as done:");
        System.out.println("\t " + doneTask);
    }

    /**
     * Show the task that has been snoozed.
     * @param date the date
     * @param changedTask the task that has been changed
     */
    public void showChangedDate(String date, String changedTask) {
        System.out.println("\t Nice! I've snoozed this task as until " + date + ":");
        System.out.println("\t " + changedTask);
    }

    /**
     * Show the size of the list.
     * @param size the size
     */
    public void showSize(int size) {
        System.out.print("\t Now you have " + size);
        if (size == 1) {
            System.out.print(" task");
        } else {
            System.out.print(" tasks");
        }
        System.out.println(" in the list.");
    }

    /**
     * Show the size of the order list.
     * @param size the size
     */
    public void showOrderListSize(int size) {
        System.out.print("\t Now you have " + size);
        if (size == 1) {
            System.out.print(" order");
        } else {
            System.out.print(" orderss");
        }
        System.out.println(" in the list.");
    }
    /**
     * Shows that a task has been added.
     * @param command ay
     * @param size ya
     */
    public void showAddCommand(String command, int size) {
        System.out.println("\t Got it. I've added this task: ");
        System.out.println("\t " + command);
        showSize(size);
    }

    /**
     * Shows that a order has been added.
     * @param command ay
     * @param size ya
     */
    public void showAddOrder(String command, int size) {
        System.out.println("\t Got it. I've added this order: ");
        System.out.println("\t " + command);
        showOrderListSize(size);
    }
    /**
     * Show the task that has been removed.
     * @param removed the task
     * @param size size of list
     */
    public void showRemovedTask(String removed, int size) {
        System.out.println("\t Noted. I've removed this task:");
        System.out.println("\t " + removed);
        showSize(size);
    }

    /**
     * Show the order that has been removed.
     * @param removed the order
     * @param size size of order list
     */
    public void showRemovedOrder(String removed, int size) {
        System.out.println("\t Noted. I've removed this order:");
        System.out.println("\t " + removed);
        showOrderListSize(size);
    }

    public void showAddedDishes(String dish, int Nb) {
        System.out.println("\t you have added the following dish: ");
        System.out.println("\t " + dish + " \tamount: " + Nb);
    }
    public void showDishes(String Dish, int Nb) {
        System.out.println(Dish + "\t orders: " + Nb);
    }
    public void showDeletedDIsh(String dish) {
        System.out.println("\t The following dish have been removed:");
        System.out.println("\t " + dish);
    }
}
