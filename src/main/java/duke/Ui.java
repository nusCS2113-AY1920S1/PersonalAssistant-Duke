package duke;

import duke.exception.DukeException;
import duke.task.Task;

import java.util.Scanner;

/**
 * Represents a <code>Ui</code> class that is responsible for Input/Output operations.
 */
public class Ui {

    protected Scanner scan = new Scanner(System.in);

    /**
     * Scans for a line of user input.
     * @return Scanner to scan for next line of user input.
     */
    public String scanLine() {
        return scan.nextLine();
    }

    /**
     * Shows the welcome logo and message for <code>Duke</code>.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
    }

    /**
     * Shows preceding welcome messages after <code>showWelcome()</code> function.
     */
    public void hello() {
        showLine();
        System.out.println("\tHello! I'm duke.Duke");
        System.out.println("\tWhat can I do for you?");
        showLine();
    }

    /**
     * Shows termination message before <code>Duke</code> exits.
     */
    public void sayBye() {
        System.out.println("\tBye. Hope to see you again soon!");
    }

    /**
     * Prints lines.
     */
    public void showLine() {
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Prints the <code>Task</code> object that was added and its relevant messages.
     * @param task <code>Task</code> object that was added.
     * @param size Number of <code>Task</code> objects in the <code>TaskList</code>.
     */
    public void addTaskMessage(Task task, int size) {
        showLine();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t\t" + task);
        if (size > 1) {
            System.out.println("\tNow you have " + size + " tasks in your list.");
        }
        else {
            System.out.println("\tNow you have " + size + " task in the list.");
        }
        showLine();
    }

    /**
     * Shows the <code>Task</code> object that has been marked as done and its relevant messages.
     * @param task <code>Task</code> object that was marked as done.
     */
    public void completeMessage(Task task) {
        showLine();
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t\t" + task);
        showLine();
    }

    /**
     * Shows the <code>Task</code> object that has been deleted and its relevant messages.
     * @param task <code>Task</code> object that was deleted.
     * @param size Number of <code>Task</code> objects in the <code>TaskList</code>.
     */
    public void deleteMessage(Task task, int size) {
        showLine();
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t\t" + task);
        if (size > 1) {
            System.out.println("\tNow you have " + size + " tasks in your list.");
        } else {
            System.out.println("\tNow you have " + size + " task in the list.");
        }
        showLine();
    }

    /**
     * Shows the error message.
     * @param exception Exception encountered.
     */
    public void showLoadingError(DukeException exception) {
        showLine();
        System.out.println("\t" + exception.getMessage());
        showLine();
    }
}
