package oof;

import oof.exception.OofException;
import oof.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Ui class that is responsible for Input/Output operations.
 */
public class Ui {

    private Scanner scan = new Scanner(System.in);

    /**
     * Scans for a line of user input.
     *
     * @return Scanner to scan for next line of user input.
     */
    public String scanLine() {
        return scan.nextLine();
    }

    /**
     * Shows the welcome logo and message for Oof.
     */
    public void printWelcomeMessage() {
        String logo = " ________  ________  ________ \n"
                + "|\\   __  \\|\\   __  \\|\\  _____\\\n"
                + "\\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\__/ \n"
                + " \\ \\  \\\\\\  \\ \\  \\\\\\  \\ \\   __\\\n"
                + "  \\ \\  \\\\\\  \\ \\  \\\\\\  \\ \\  \\_|\n"
                + "   \\ \\_______\\ \\_______\\ \\__\\ \n"
                + "    \\|_______|\\|_______|\\|__|\n";
        System.out.println("Hello from\n" + logo);
    }

    /**
     * Shows preceding welcome messages after printWelcomeMessage() function.
     */
    public void hello() {
        printLine();
        System.out.println("\tHello! I'm OOF");
        System.out.println("\tWhat can I do for you?");
        printLine();
    }

    /**
     * Shows termination message before Oof exits.
     */
    public void printByeMessage() {
        System.out.println("\tBye. Hope to see you again soon!");
    }

    /**
     * Prints lines.
     */
    public void printLine() {
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Prints the Task object that was added and its relevant messages.
     *
     * @param task Task object that was added.
     * @param size Number of Task objects in the TaskList.
     */
    public void addTaskMessage(Task task, int size) {
        printLine();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t\t" + task);
        if (size > 1) {
            System.out.println("\tNow you have " + size + " tasks in your list.");
        } else {
            System.out.println("\tNow you have " + size + " task in the list.");
        }
        printLine();
    }

    /**
     * Shows the Task object that has been marked as done and its relevant messages.
     *
     * @param task Task object that was marked as done.
     */
    public void completeMessage(Task task) {
        printLine();
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t\t" + task);
        printLine();
    }

    /**
     * Shows the Task object that has been deleted and its relevant messages.
     *
     * @param task Task object that was deleted.
     * @param size Number of Task objects in the TaskList.
     */
    public void deleteMessage(Task task, int size) {
        printLine();
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t\t" + task);
        if (size > 1) {
            System.out.println("\tNow you have " + size + " tasks in your list.");
        } else {
            System.out.println("\tNow you have " + size + " task in the list.");
        }
        printLine();
    }

    /**
     * Shows the error message.
     *
     * @param exception Exception encountered.
     */
    public void printOofException(OofException exception) {
        printLine();
        System.out.println("\t" + exception.getMessage());
        printLine();
    }

    /**
     * Displays the Task to be snoozed.
     *
     * @param task Task to be snoozed.
     */
    public void printSnoozeMessage(Task task) {
        printLine();
        System.out.println("\tI have changed the date of this task!");
        System.out.println("\t\t" + task);
        printLine();
    }

    /**
     * Retrieves a new Timestamp from the user for the Task
     * to be snoozed.
     *
     * @return Timestamp input by user.
     */
    public String getTimeStamp() {
        System.out.print("\tPlease enter the new date: ");
        return scanLine();
    }

    /**
     * Prints a continue prompt and waits for user input.
     *
     * @return true if user inputs "Y", false if user inputs "N"
     */
    public boolean printContinuePrompt() {
        String input = "";
        while (true) {
            System.out.println("Continue anyway? (Y/N)");
            input = scanLine();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            }
        }
    }

    /**
     * Prints a warning regarding event clashes.
     */
    public void printClashWarning() {
        System.out.println("Warning! Event being added clashes with the following events:");
    }

    /**
     * Prints a reminder regarding upcoming deadlines.
     */
    public void printReminder() {
        System.out.println("\tReminder these tasks have upcoming deadlines:");
    }

    /**
     * Prints the details of an upcoming deadline.
     *
     * @param count Position of upcoming deadline in reminder list.
     * @param task  Task object of upcoming deadline.
     */
    public void printUpcomingDeadline(int count, Task task) {
        System.out.println("\t" + count + "." + task);
    }

    /**
     * Prints all tasks scheduled on the provided date.
     *
     * @param scheduledTasks List of all Tasks scheduled on the date provided.
     * @param date           Date parameter provided by user.
     */
    public void printScheduledTasks(TaskList scheduledTasks, String date) {
        printLine();
        System.out.println("\t Here are your tasks for" + date + ": ");
        for (int i = 0; i < scheduledTasks.getSize(); i++) {
            System.out.println("\t" + (i + 1) + ". " + scheduledTasks.getTask(i));
        }
        printLine();
    }

    /**
     * Prints list of matching tasks.
     *
     * @param matchedTasks ArrayList containing matching tasks.
     */
    public void printMatchingTasks(ArrayList<Task> matchedTasks) {
        if (matchedTasks.size() == 0) {
            System.out.println("\tThere are no matching tasks in your list!");
        } else {
            printLine();
            System.out.println("\tHere are the matching tasks in your list:");
            for (int i = 0; i < matchedTasks.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + matchedTasks.get(i));
            }
            printLine();
        }
    }

    /**
     * Prints all tasks in TaskList.
     *
     * @param arr TaskList containing saved tasks.
     */
    public void printTaskList(TaskList arr) {
        printLine();
        System.out.println("\t Here are the tasks in your list:");
        for (int i = 0; i < arr.getSize(); i++) {
            System.out.println("\t" + (i + 1) + ". " + arr.getTask(i));
        }
        printLine();
    }
}
