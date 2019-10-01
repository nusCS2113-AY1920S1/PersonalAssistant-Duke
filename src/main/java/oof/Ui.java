package oof;

import oof.exception.OofException;
import oof.task.Event;
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
     * Prints 3D ascii logo OOF.
     */
    public void printOofLogo() {
        String logo = "\t ________  ________  ________ \n"
                + "\t|\\   __  \\|\\   __  \\|\\  _____\\\n"
                + "\t\\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\__/ \n"
                + "\t \\ \\  \\\\\\  \\ \\  \\\\\\  \\ \\   __\\\n"
                + "\t  \\ \\  \\\\\\  \\ \\  \\\\\\  \\ \\  \\_|\n"
                + "\t   \\ \\_______\\ \\_______\\ \\__\\ \n"
                + "\t    \\|_______|\\|_______|\\|__|\n";
        System.out.println(logo);
    }

    /**
     * Prints welcome message for OOF.
     */
    public void hello() {
        printLine();
        printOofLogo();
        System.out.println("\tHello! I'm OOF");
        System.out.println("\tWhat can I do for you?");
        printLine();
    }

    /**
     * Shows termination message before OOF exits.
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
        System.out.println("\tPlease enter the new date: ");
        return scanLine();
    }

    /**
     * Prints a continue prompt and waits for user input.
     *
     * @return User input if it is equals to "Y" or "N"
     */
    public String printContinuePrompt() {
        String input = "";
        while (true) {
            System.out.println("Continue anyway? (Y/N)");
            input = scanLine();
            if (input.equals("Y") || input.equals("N")) {
                return input;
            }
        }
    }

    /**
     * Prints a warning regarding event clashes.
     */
    public void printClashWarning(ArrayList<Event> eventClashes) {
        System.out.println("Warning! Event being added clashes with the following events:");
        for (Event e : eventClashes) {
            System.out.println(e.toString());
        }
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
