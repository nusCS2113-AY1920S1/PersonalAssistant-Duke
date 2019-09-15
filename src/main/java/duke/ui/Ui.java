package duke.ui;

import duke.tasks.Task;
import duke.tasks.UniqueTaskList;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles user input and messages shown to user of this application.
 */
public class Ui {
    private static final String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private static final String MESSAGE_WELCOME = "Hello! I'm duke.Duke\nWhat can I do for you?\n";
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";
    private static final String MESSAGE_MARK_DONE = "Nice! I've marked this task as done:\n  ";
    private static final String MESSAGE_ADDITION = "Got it. I've added this task:\n  ";
    private static final String MESSAGE_DELETE = "Alright! I've removed this task:\n  ";
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prints a welcome message to the user, which happens at startup.
     */
    public void showWelcome() {
        System.out.println("Hello from\n" + logo);
        System.out.println(MESSAGE_WELCOME);
    }

    /**
     * Prints an error message to the user.
     */
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Prints a bye message to the user, which happens upon exit.
     */
    public void showBye() {
        System.out.println(MESSAGE_BYE);
    }

    /**
     * Prints the list of duke.tasks.
     */
    public void showList(UniqueTaskList tasks) {
        int i = 1;
        for (Task t : tasks) {
            System.out.println(i + ". " + t);
            i += 1;
        }
    }

    /**
     * Prints the description of a task.
     */
    public void showAdd(Task task) {
        System.out.println(MESSAGE_ADDITION + task);
    }

    /**
     * Prints the task that is mark done.
     */
    public void showMarkDone(Task task) {
        System.out.println(MESSAGE_MARK_DONE + task);
    }

    /**
     * Prints the task that is deleted.
     */
    public void showDelete(Task task) {
        System.out.println(MESSAGE_DELETE + task);
    }

    /**
     * Scans the next line from standard input.
     *
     * @return The String corresponding to the user input.
     */
    public String readCommand() {
        String line = scanner.nextLine().strip();
        System.out.println("\n" + line);
        return line;
    }

    /**
     * temp: getters for messages to be printed by duke
     * @return
     */
    public String getWelcome() {
        return ("Hello from\n" + logo + "\n" + MESSAGE_WELCOME);
    }

    public String getError(String errorMessage) {
        return errorMessage;
    }

    public String getByeMessage() {
        return MESSAGE_BYE;
    }

    public String getList(UniqueTaskList tasks) {
        String result = "Here are the list of tasks:\n";
        int i = 1;
        for (Task t : tasks) {
            result += (i + ". " + t + "\n");
            i += 1;
        }
        return result;
    }

    public String getTaskDesc(Task task) {
        return (MESSAGE_ADDITION + task);
    }

    public String getShowMarkDone(Task task) {
        return (MESSAGE_MARK_DONE + task);
    }

    public String getDelete(Task task) {
        return (MESSAGE_DELETE + task);
    }
}
