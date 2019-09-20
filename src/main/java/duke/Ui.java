package duke;

import duke.task.Task;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * duke.Ui is a class that handles all interactions to the user.
 */
public class Ui {

    protected ArrayList<String> messageArray;

    public static void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
    }

    /**
     * This method prints the strings of text from 'msg' with the proper format. Each element
     * from 'msg' is a line of text to be printed.
     *
     * @param msg ArrayList of strings containing the messages to be printed.
     */
    public static void printMsg(ArrayList<String> msg) {
        System.out.println("    ____________________________________________________________");
        for (String outputMsg : msg) {
            System.out.println("     " + outputMsg);
        }
        System.out.println("    ____________________________________________________________\n");
    }

    // Echoes when an item is added

    /**
     * This method prints the details of the specified task and specified duke.task.TaskList size.
     * <p>
     * This method is typically called when a task is created, so that the user can
     * check the details of the created task.
     * </p>
     *
     * @param currTask duke.task.Task to be printed.
     * @param listSize Size of the duke.task.TaskList.
     */
    public static void echoAdd(Task currTask, int listSize) {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Got it. I've added this task: ",
                "  " + currTask.getTask(),
                "Now you have " + listSize + " task(s) in the list."
        ));
        printMsg(msg);
    }

    public static void showLine() {
        System.out.println("    ____________________________________________________________");
    }

    private static String line = "\t____________________________________________________________";
    public static void printDateTimeFormatError() {
        ArrayList<String> msg = new ArrayList<String>();
        msg.add("Please use the format 'DD/MM/YYYY HHmm'!");
        Ui.printMsg(msg);
    }

    public static void printDateFormatError() {
        ArrayList<String> msg = new ArrayList<String>();
        msg.add("Please use the format 'DD/MM/YYYY'!");
        Ui.printMsg(msg);
    }

    public static void printFixDurationTaskError() {
        System.out.println("Sorry, please enter a valid fixed duration task.");
    }

    /**
     * This method will print the error message when the user enter a invalid recurring event.
     */
    public static void printRecurringTaskError() {
        System.out.println("Sorry, please enter a valid recurring event.");
    }

    /**
     * This method will print the error message when the user enter a invalid day of the week.
     */
    public static void printInvaidDayInput() {
        System.out.println("Sorry,please enter a valid day of the week.");
    }

    /**
     * This method will print the error message when the user enter a invalid do after event.
     */
    public static void printInvaidDoAfterInput() {
        System.out.println("Please enter a valid do after");
    }

    /**
     * This method will print the error message when the user enter a time that conflicts with a task
     * that's in the task list.
     */
    public static void printTimeConflictError(Task conflictingTask) {
        ArrayList<String> msg = new ArrayList<String>();
        msg.add("I'm sorry, an error has occured!");
        msg.add("The time you have entered conflicts with the following task: ");
        msg.add("  " + conflictingTask.getTask());
        msg.add("Try looking for another time. :)");
        Ui.printMsg(msg);
    }

    /**
     * Prints the snoozed task with new date after successfully snoozing
     * @param snoozedTask task that was snoozed
     */
    public static void snoozedTaskPrinter(Task snoozedTask) {
        System.out.println(line);
        System.out.println("\tNoted. I have snoozed this task:");
        System.out.println("\t" + snoozedTask.getTaskDescription() + "until " + snoozedTask.getDateStr());
        System.out.println(line);
    }

    /**
     * Prints an error message for no date in task
     */
    public static void noDateToSnoozePrinter(Task taskToSnooze) {
        System.out.println(line);
        System.out.println("\tOOPS! " + taskToSnooze + " do not have a date to snooze!");
        System.out.println(line);
    }

    /**
     * Prints a default error message
     */
    public static void errorMsgPrinter() {
        System.out.println(line);
        System.out.println("\tOOPS! An error has occurred.");
        System.out.println(line);
    }

    /**
     * Prints error message when taskNum is not associated to a task.
     * @param taskNum the task number that is not associated with a task.
     */
    public static void printNoTaskAssocError(int taskNum) {
        System.out.println(line);
        System.out.println("\t" + taskNum + " is not associated to any task number.");
        System.out.println("\tUse 'list' to check the tasks that are here first!");
        System.out.println(line);
    }
}
