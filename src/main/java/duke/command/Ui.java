package duke.command;

import duke.task.Snoozeable;
import duke.task.Task;
import java.util.List;
import java.util.Scanner;

public class Ui {
    /** Reference to Scanner. */
    private Scanner sc;

    /**
     * Instantiates Scanner class.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints an indented line.
     */
    public void beginBlock() {
        printHR();
    }

    /**
     * Prints an indented line.
     */
    public void endBlock() {
        printHR();
    }

    /**
     * Prints welcome message.
     */
    public void showWelcome() {
        beginBlock();
        printIndented("Hello! I'm Duke");
        printIndented("What can I do for you?");
        endBlock();
    }

    /**
     * Prints farewell message.
     */
    public void showBye() {
        printIndented("Bye. Hope to see you again soon!");
    }

    /**
     * Prints error to user.
     *
     * @param message Error message to be displayed
     */
    public void showError(String message) {
        printIndented(message);
    }

    /**
     * Prints the task that was added.
     *
     * @param tasks List of all tasks
     * @param task Added task
     */
    public void showTaskAdded(List<Task> tasks, Task task) {
        printIndented("Got it . I've added this task:");
        printIndented("  " + task);
        showNumTasks(tasks);
    }

    /**
     * Displays Task objects in list given.
     *
     * @param tasks List of Task objects
     */
    public void showTaskList(List<Task> tasks) {
        printIndented("Here are the tasks in your list:");
        showTasks(tasks);
    }

    /**
     * Displays search results to user.
     *
     * @param tasks List containing tasks from user
     */
    public void showSearchResult(List<Task> tasks) {
        printIndented("Here are the matching tasks in your list:");
        showTasks(tasks);
    }

    /**
     * Shows the task that was just snoozed.
     *
     * @param task The task that was just snoozed
     */
    public void showSnoozedTask(Snoozeable task) {
        printIndented("Noted. I've snoozed this task:");
        printIndented("  " + task);
    }

    /**
     * Shows the task that was just deleted.
     *
     * @param tasks List of all tasks
     * @param task  The task that was just deleted
     */
    public void showDeletedTask(List<Task> tasks, Task task) {
        printIndented("Noted. I've removed this task:");
        printIndented("  " + task);
        showNumTasks(tasks);
    }

    /**
     * Prints message for task completion.
     *
     * @param task Completed task
     */
    public void showDoneTask(Task task) {
        printIndented("Nice! I've marked this task as done:");
        printIndented("  " + task);
    }

    /**
     * Gets next line from user inputs.
     *
     * @return String containing user input
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Prints a string accompanied with indentation.
     *
     * @param line String containing description to be indented
     */
    private void printIndented(String line) {
        System.out.println("    " + line);
    }

    /**
     * Displays the tasks in a list.
     *
     * @param tasks List containing user tasks
     */
    private void showNumTasks(List<Task> tasks) {
        printIndented("Now you have "
                + tasks.size()
                + (tasks.size() > 1 ? " tasks" : " task")
                + " in the list.");
    }

    /**
     * Displays tasks in a list.
     *
     * @param tasks List containing user tasks
     */
    public void showTasks(List<Task> tasks) {
        int counter = 1;
        for (Task task : tasks) {
            printIndented(counter++ + ". " + task);
        }
    }

    /**
     * Shows no deadlines present.
     */
    public void showNoDeadlines() {
        printIndented("You have no pending deadlines.");
    }

    /**
     * Displays the list of deadlines present in user list.
     *
     * @param tasks List of all tasks
     */
    public void showDeadlines(List<Task> tasks) {
        printIndented("You currently have these deadlines.");
        showTasks(tasks);
    }

    /**
     * Prints a straight line.
     */
    private void printHR() {
        printIndented("____________________________________________________________");
    }
}