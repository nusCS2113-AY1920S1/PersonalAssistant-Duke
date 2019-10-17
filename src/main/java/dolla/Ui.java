package dolla;


//import dolla.task.;


import dolla.task.*;
import sort.Sort;
import sort.SortDate;
import sort.SortDescription;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * duke.Ui is a class that handles all interactions to the user.
 */
public class Ui {

    private static String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    public static String line = "\t____________________________________________________________";

    public static void showWelcome() {
        System.out.println("Hello from\n" + logo);
    }

    /**
     * This method prints the strings of text from 'msg' with the proper format. Each element
     * from 'msg' is a line of text to be printed.
     *
     * @param msg ArrayList of strings containing the messages to be printed.
     */
    public static void printMsg(ArrayList<String> msg) {
        System.out.println(line);
        for (String outputMsg : msg) {
            System.out.println("\t" + outputMsg);
        }
        System.out.println(line);
    }

    /**
     * This method prints the strings of text from 's' with the proper format. Each element
     * from 's' is a line of text to be printed.
     *
     * @param s containing the messages to be printed.
     */
    public static void printMsg(String... s) {
        final StringBuilder messageAccumulator = new StringBuilder();
        for (String str: s) {
            messageAccumulator.append(str);
        }
        System.out.println(line);
        System.out.println("\t" + messageAccumulator);
        System.out.println(line);
    }

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

    /**
     * This method prints the details of the specified entry.
     * <p>
     * This method is typically called when an entry is entered, so that the user can
     * check the details of the created entry.
     * </p>
     *
     * @param currEntry Entry to be printed.
     */
    public static void echoAddEntry(Entry currEntry) {
        System.out.println(line);
        System.out.println("\tGot it. I've added this entry: ");
        System.out.println("\t" + currEntry.getLogText());
        System.out.println(line);
    }

    public static void echoAddDebt(Debt currDebt) {
        System.out.println(line);
        System.out.println("\tGot it. I've added this debt: ");
        System.out.println("\t" + currDebt.getLogText());
        System.out.println(line);
    }

    /**
     * Prints an error message when date in not in the format 'DD/MM/YYYY HHmm'.
     */
    public static void printDateTimeFormatError() {
        System.out.println(line);
        System.out.println("Please use the format 'DD/MM/YYYY HHmm'!");
        System.out.println(line);
    }

    /**
     * Prints an error message when date in not in the format 'DD/MM/YYYY'.
     */
    public static void printDateFormatError() {
        System.out.println(line);
        System.out.println("\tPlease use the format 'DD/MM/YYYY'!");
        System.out.println(line);
    }

    /**
     * Prints an error message when fixed duration entered is invalid.
     */
    public static void printFixDurationTaskError() {
        System.out.println(line);
        System.out.println("\tSorry, please enter a valid fixed duration task.");
        System.out.println(line);
    }

    /**
     * This method will print the error message when the user enter a invalid recurring event.
     */
    public static void printRecurringTaskError() {
        System.out.println(line);
        System.out.println("\tSorry, please enter a valid recurring event.");
        System.out.println(line);
    }

    /**
     * This method will print the error message when the user enter a invalid day of the week.
     */
    public static void printInvalidDayInput() {
        System.out.println(line);
        System.out.println("\tSorry, please enter a valid day of the week.");
        System.out.println(line);
    }

    /**
     * This method will print the error message when the user enter a invalid do after event.
     */
    public static void printInvalidDoAfterInput() {
        System.out.println(line);
        System.out.println("\tPlease enter a valid do after");
        System.out.println(line);
    }

    /**
     * This method will print the error message when the user enter a time that conflicts with a task
     * that's in the task list.
     */
    public static void printTimeConflictError(Task conflictingTask) {
        System.out.println(line);
        System.out.println("\tI'm sorry, an error has occurred!");
        System.out.println("\tThe time you have entered conflicts with the following task: ");
        System.out.println("\t  " + conflictingTask.getTask());
        System.out.println("Try looking for another time. :)");
        System.out.println(line);
    }

    /**
     * Prints the snoozed task with new date after successfully snoozing.
     * @param snoozedTask task that was snoozed
     */
    public static void printSnoozedTask(Task snoozedTask) {
        System.out.println(line);
        System.out.println("\tNoted. I have snoozed this task:");
        System.out.println("\t" + snoozedTask.getTaskDescription() + " " + snoozedTask.getDateStr());
        System.out.println(line);
    }

    /**
     * Prints an error message for no date in task.
     */
    public static void printNoDateToSnoozeError(Task taskToSnooze) {
        System.out.println(line);
        System.out.println("\tOOPS! " + taskToSnooze + " do not have a date to snooze!");
        System.out.println(line);
    }

    /**
     * Prints a default error message.
     */
    public static void printErrorMsg() {
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

    /**
     * Prints error message when string parsed is not an integer.
     * @param str to be parsed to an integer
     */
    public static void printInvalidNumberError(String str) {
        System.out.println(line);
        System.out.println("\t" + str + " is not a number. Please use a number instead!");
        System.out.println(line);
    }

    /**
     * Prints error message when the new date to snooze until is before the old date.
     */
    public static void printOldDateIsAfterError() {
        System.out.println(line);
        System.out.println("\tOOPS! The new date given should be after the previous date!");
        System.out.println(line);
    }

    /**
     * Prints error message when command given is invalid.
     */
    public static void printInvalidCommandError() {
        System.out.println(line);
        System.out.println("\tOOPS! The command is invalid. Please enter a valid command");
        System.out.println(line);
    }

    public static void printInvalidEntryType() {
        System.out.println("\tPlease specify the type of entry you want to add: income or expense.");
    }

    /**
     * Prints error message when user tries to add income/expense, but inputs the wrong format.
     */
    public static void printInvalidEntryFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'add income(/expense) [AMOUNT] [DESCRIPTION] /on [DATE] {/tag [TAG]}'"
                + "");
        System.out.println(line);
    }

    public static void printInvalidDebtFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format"
                + "'owe(/borrow) [NAME] [AMOUNT] [DESCRIPTION]'");
        System.out.println(line);
    }

    /**
     * Prints Dolla's new mode after being updated.
     * @param newMode The new mode to be switched.
     */
    public static void printModeUpdated(String newMode) {
        System.out.println(line);
        System.out.println("\tMode changed to " + newMode + "!");
        System.out.println(line);
    }

    public static void printEmptyListError(String mode) {
        System.out.println(line);
        System.out.println("\tYou haven't added any " + mode + " yet!");
        System.out.println(line);
    }


   // public static void printList(String mode, LogList entryList) {
    public static void printList(String mode, LogList logList) {

        System.out.println(line);
        System.out.println("\tHere are the " + mode + " that you have added:");
        for (int i = 0; i < logList.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + logList.get().get(i).getLogText());
        }
        System.out.println(line);
    }

    public static void printSortedList(ArrayList<Log> list,String type) {
        System.out.println(line);
        if(type.equals("date")) {
            System.out.println("sorting date.........");
        } else if(type.equals("description")) {
            System.out.println("sorting description.........");
        } else if(type.equals("name")) {
            System.out.println("sorting name.........");
        }

        for (int i = 0; i < list.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + list.get(i).getLogText());
        }
        System.out.println(line);
    }
}
