package oof;

import oof.exception.OofException;
import oof.task.Event;
import oof.task.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Ui class that is responsible for Input/Output operations.
 */
public class Ui {

    private Scanner scan = new Scanner(System.in);
    private static final int DAY_FIRST = 1;
    private static final int DAYS_IN_WEEK = 7;
    private static final int INDEX_SPACE = 0;
    private static final int INDEX_START_OF_ARRAY = 0;

    /**
     * Scans for an integer of user input.
     *
     * @return Scanner to scan for an integer of user input.
     */
    public int scanInt() {
        return scan.nextInt();
    }

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
     * Displays the newly added recurring tasks.
     *
     * @param arr TaskList containing saved tasks.
     */
    public void printRecurringMessage(TaskList arr) {
        printLine();
        System.out.println("\tI have added recurring tasks:");
        printTaskList(arr);
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
     * Prints list of options for the recurring frequency of a task.
     */
    public void printRecurringOptions() {
        String options = "\tListed are the available options for recurring tasks!\n"
                + "\t1. Daily\n"
                + "\t2. Weekly\n"
                + "\t3. Monthly\n"
                + "\t4. Yearly\n"
                + "\tPlease choose one of the four options for your recurring frequency!\n";
        System.out.println(options);
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

    /**

     * Prints calendar.
     *
     * @param yearMonth Object containing month and year information.
     */
    public void printCalendar(YearMonth yearMonth) {
        printCalendarHeader(yearMonth);
        printCalendarBody(yearMonth);
    }

    /**
     * Prints calendar header.
     *
     * @param yearMonth Object containing month and year information.
     */
    public void printCalendarHeader(YearMonth yearMonth) {
        String[] months = {"", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST",
                "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        String month = months[yearMonth.getMonthValue()];
        String year = Integer.toString(yearMonth.getYear());
        System.out.println(month + " " + year);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("|    SUN    |    MON    |    TUE    |    WED    |    THU    |    FRI    |    SAT    |");
        System.out.println("-------------------------------------------------------------------------------------");
    }

    /**
     * Prints calendar body.
     *
     * @param yearMonth Object containing month and year information.
     */
    public void printCalendarBody(YearMonth yearMonth) {
        String[] date = {"  ", " 1", " 2", " 3", " 4", " 5", " 6", " 7",
                " 8", " 9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31"};
        ArrayList<String> calendarDates = new ArrayList<>();
        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            calendarDates.add(date[i]);
        }

        DayOfWeek firstDayOfMonth = yearMonth.atDay(DAY_FIRST).getDayOfWeek();
        if (firstDayOfMonth != DayOfWeek.SUNDAY) {
            for (int dayCount = 0; dayCount < firstDayOfMonth.getValue(); dayCount++) {
                calendarDates.add(INDEX_START_OF_ARRAY, date[INDEX_SPACE]);
            }
        }

        while (calendarDates.size() % DAYS_IN_WEEK != 0) {
            calendarDates.add(date[INDEX_SPACE]);
        }

        int numberOfWeeks = (int) Math.ceil((double) calendarDates.size() / DAYS_IN_WEEK);
        for (int week = 0; week < numberOfWeeks; week++) {
            int dayIndex = week * DAYS_IN_WEEK;
            System.out.print("|");
            for (int day = 0; day < DAYS_IN_WEEK; day++) {
                System.out.print(" " + calendarDates.get(dayIndex) + "        |");
                dayIndex++;
            }
            System.out.println();
            System.out.println("|           |           |           |           |           |           |           |");
            System.out.println("|           |           |           |           |           |           |           |");
            System.out.println("|           |           |           |           |           |           |           |");
            System.out.println("|           |           |           |           |           |           |           |");
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

     * Prints all commands available to user.
     *
     * @param commands ArrayList containing list of commands available to user
     */
    public void printHelpCommands(ArrayList commands) {
        printLine();
        System.out.println("\tHere is a list of OOF commands: ");
        for (int i = 0; i < commands.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + commands.get(i));
        }
        printLine();
    }
}
