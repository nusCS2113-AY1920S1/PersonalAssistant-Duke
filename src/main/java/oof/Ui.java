package oof;

import oof.exception.OofException;
import oof.task.Event;
import oof.task.Task;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Represents a Ui class that is responsible for Input/Output operations.
 */
public class Ui {

    private Scanner scan = new Scanner(System.in);
    private static final int DATE_SPACES = 3;
    private static final int SPLIT_EVEN = 2;
    private static final int DAY_FIRST = 1;
    private static final int DAYS_IN_WEEK = 7;
    private static final int WEEKS_IN_MONTH = 4;
    private static final int INDEX_SPACE = 0;
    private static final int INDEX_START_OF_ARRAY = 0;
    private static final int TEXT_SIZE_SHORT = 13;
    private static final int TEXT_SIZE_LONG = 19;
    private static final int DESCRIPTION_SHORT_START = 0;
    private static final int DESCRIPTION_SHORT_END = 11;
    private static final int DESCRIPTION_LONG_START = 0;
    private static final int DESCRIPTION_LONG_END = 17;
    private static final int LEAST_COL_SIZE = 19;
    private static final int TIME = 0;
    private static final int DESCRIPTION = 1;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BRIGHT_WHITE = "\u001B[97m";
    private static final String ANSI_BRIGHT_BG_YELLOW = "\u001B[103m";
    private static final String[] colouredText = {ANSI_RED, ANSI_GREEN, ANSI_YELLOW,
            ANSI_BLUE, ANSI_PURPLE, ANSI_CYAN, ANSI_WHITE};
    private Storage storage = new Storage();

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
     * Retrieves a new Timestamp from the user for the Task to be snoozed.
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
     * Prints a reminder that the user has no deadlines.
     */
    public void printNoDeadlines() {
        System.out.println("\tYou have no upcoming deadlines :)");
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
    public void printTasksByDate(TaskList scheduledTasks, String date) {
        printLine();
        System.out.println("\t Here are your tasks for " + date + ": ");
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
     * Displays the newly added recurring tasks.
     *
     * @param arr TaskList containing saved tasks.
     */
    public void printRecurringMessage(TaskList arr) {
        printLine();
        System.out.println("\tI have added recurring tasks:");
        printTaskList(arr);
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
     * Prints and applies format for command list available to user.
     *
     * @throws OofException if readManual method fails.
     */
    public void printHelpCommands() throws OofException {
        ArrayList<String> command = storage.readManual();
        for (int i = 0; i < command.size(); i++) {
            System.out.println("\t" + command.get(i));
        }
        printLine();
    }

    /**
     * Prints instruction related to individual command available to user.
     *
     * @param command instruction of command given by user.
     */
    public void printHelpCommand(String command) {
        printLine();
        System.out.println("\t" + command);
        printLine();
    }

    /**
     * Prints the tasks for a particular week.
     *
     * @param tasks Tasks for the particular week to be printed.
     * @param startDate Starting date of the week.
     * @param largestTaskSize Size of the day with the largest number of tasks.
     * @param largestColSize Size of the largest column in the View Week output.
     */
    public void printViewWeek(ArrayList<ArrayList<String[]>> tasks, Date startDate, int largestTaskSize,
                              int largestColSize) {
        printViewWeekHeader(largestColSize);
        printViewWeekBody(startDate, largestColSize);
        printViewWeekDetails(tasks, largestTaskSize, largestColSize);
    }

    /**
     * Prints header for ViewWeek command.
     *
     * @param largestColSize Size of the largest column in the View Week output.
     */
    private void printViewWeekHeader(int largestColSize) {
        String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        if (!isEven(largestColSize)) {
            largestColSize++;
        }
        int numberOfHyphens = ((largestColSize + DATE_SPACES) * DAYS_IN_WEEK) + DAYS_IN_WEEK + DAY_FIRST;
        System.out.print(ANSI_BRIGHT_WHITE + ANSI_BRIGHT_BG_YELLOW);
        printHyphens(numberOfHyphens);
        System.out.print(ANSI_BRIGHT_WHITE + ANSI_BRIGHT_BG_YELLOW + "|");
        System.out.print(ANSI_RESET);
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            printHeaderSpaces(largestColSize);
            System.out.print(days[i]);
            printHeaderSpaces(largestColSize);
            if (i != DAYS_IN_WEEK - 1) {
                System.out.print("|");
            } else {
                printViewWeekBorder();
                System.out.println();
            }
        }
        printHyphens(numberOfHyphens);
    }

    /**
     * Prints hyphens when the ViewWeek output needs to be resized.
     *
     * @param numberOfHyphens Number of hyphens to be printed.
     */
    private void printHyphens(int numberOfHyphens) {
        for (int i = 0; i < numberOfHyphens; i++) {
            System.out.print(ANSI_BRIGHT_WHITE + ANSI_BRIGHT_BG_YELLOW + "-");
        }
        System.out.println(ANSI_RESET);
    }

    /**
     * Prints only spaces for empty rows in View Week output.
     *
     * @param largestColSize Size of the largest column in the View Week output.
     */
    private void printBodySpaces(int largestColSize) {
        for (int i = 0; i < largestColSize; i++) {
            System.out.print(" ");
        }
    }

    /**
     * Print spaces when the ViewWeek output needs to be resized.
     *
     * @param largestColSize Size of the largest column in the View Week output.
     */
    private void printHeaderSpaces(int largestColSize) {
        for (int i = 0; i < largestColSize / SPLIT_EVEN; i++) {
            System.out.print(ANSI_BRIGHT_WHITE + ANSI_BRIGHT_BG_YELLOW + " ");
        }
    }

    /**
     * Checks if number is even.
     *
     * @param number Number to be checked.
     * @return True if the number is even, false otherwise.
     */
    private boolean isEven(int number) {
        return number % SPLIT_EVEN == 0;
    }

    /**
     * Prints the body for ViewWeek command.
     *
     * @param startDate Starting date of the week.
     * @param largestColSize Size of the largest column in the View Week output.
     */
    private void printViewWeekBody(Date startDate,int largestColSize) {
        ArrayList<String> calendarDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            if (i >= DAY_FIRST) {
                calendar.add(Calendar.DATE, DAY_FIRST);
            }
            Date currentDate = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String date = formatter.format(currentDate);
            calendarDates.add(date);
        }
        printViewWeekDates(calendarDates, largestColSize);
    }

    /**
     * Prints the dates for the ViewWeek command.
     *
     * @param calendarDates List of dates for the week to be printed.
     * @param largestColSize Size of the largest column in the View Week output.
     */
    private void printViewWeekDates(ArrayList<String> calendarDates, int largestColSize) {
        printViewWeekBorder();
        String spaces = "";
        if (!isEven(largestColSize)) {
            largestColSize++;
        }
        for (int i = 0; i < largestColSize + DATE_SPACES - DESCRIPTION_SHORT_END; i++) {
            spaces += " ";
        }
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            if (i != DAYS_IN_WEEK - 1) {
                System.out.print(" " + colouredText[i] + calendarDates.get(i) + ANSI_RESET + spaces + "|");
            }
        }
        System.out.print(" " + colouredText[DAYS_IN_WEEK - 1] + calendarDates.get(DAYS_IN_WEEK - 1) + ANSI_RESET
                + spaces);
        printViewWeekBorder();
        System.out.println();
    }

    /**
     * Prints the details of tasks in the ViewWeek command.
     *
     * @param tasks Tasks to be printed for the particular week.
     * @param largestTaskNo Size of the day with the largest number of tasks.
     * @param largestColSize Size of the largest column in the View Week output.
     */
    private void printViewWeekDetails(ArrayList<ArrayList<String[]>> tasks, int largestTaskNo, int largestColSize) {
        printEntryBodySpace(largestColSize);
        for (int taskNo = 0; taskNo < largestTaskNo; taskNo++) {
            printDetailsByLine(tasks, taskNo, largestColSize);
        }
        printEntryBodySpace(largestColSize);
        if (!isEven(largestColSize)) {
            largestColSize++;
        }
        int numberOfHyphens = ((largestColSize + DATE_SPACES) * DAYS_IN_WEEK) + DAYS_IN_WEEK + DAY_FIRST;
        printHyphens(numberOfHyphens);
    }

    /**
     * Prints the tasks for ViewWeek command line by line.
     *
     * @param tasks Tasks for the particular week to be printed.
     * @param taskNo Current index of the task that is being printed.
     * @param largestColSize Size of the largest column in the View Week output.
     */
    private void printDetailsByLine(ArrayList<ArrayList<String[]>> tasks, int taskNo, int largestColSize) {
        printViewWeekBorder();
        for (int dayInWeek = 0; dayInWeek < DAYS_IN_WEEK; dayInWeek++) {
            int colSize = tasks.get(dayInWeek).size();
            if (taskNo < colSize) {
                String task = getTaskDetails(tasks.get(dayInWeek).get(taskNo));
                task = padTaskDetails(task, largestColSize);
                System.out.print(task);
                if (dayInWeek != DAYS_IN_WEEK - 1) {
                    System.out.print("|");
                } else {
                    printViewWeekBorder();
                    System.out.println();
                }
            } else {
                if (!isEven(largestColSize)) {
                    largestColSize++;
                }
                printBodySpaces(largestColSize + DATE_SPACES);
                if (dayInWeek != DAYS_IN_WEEK - 1) {
                    System.out.print("|");
                } else {
                    printViewWeekBorder();
                    System.out.println();
                }
            }
        }
    }

    /**
     * Prints the side borders of the View Week output.
     */
    private void printViewWeekBorder() {
        System.out.print(ANSI_BRIGHT_WHITE + ANSI_BRIGHT_BG_YELLOW + "|");
        System.out.print(ANSI_RESET);
    }

    /**
     * Pads the details of a task to fit into a day of the ViewWeek command output.
     *
     * @param details Details of a task.
     * @param largestColSize Size of the largest column in the ViewWeek command output.
     * @return Padded details of a task.
     */
    private String padTaskDetails(String details, int largestColSize) {
        if (!isEven(largestColSize)) {
            largestColSize++;
        }
        String newDetails = " " + details;
        while (newDetails.length() < largestColSize + DATE_SPACES) {
            newDetails += " ";
        }
        return newDetails;
    }

    /**
     * Outputs the details of a task in a string.
     *
     * @param details Array of the details containing time and description.
     * @return String containing the details of a task.
     */
    private String getTaskDetails(String[] details) {
        return details[DESCRIPTION] + " " + details[TIME];
    }

    /**
     * Prints the empty columns in the View Week body.
     *
     * @param largestColSize Longest possible description for task.
     */
    public void printEntryBodySpace(int largestColSize) {
        printViewWeekBorder();
        if (!isEven(largestColSize)) {
            largestColSize++;
        }
        for (int day = 0; day < DAYS_IN_WEEK; day++) {
            for (int emptySpaceNo = 0; emptySpaceNo < largestColSize + DATE_SPACES; emptySpaceNo++) {
                System.out.print(" ");
            }
            if (day != DAYS_IN_WEEK - 1) {
                System.out.print("|");
            } else {
                printViewWeekBorder();
                System.out.println();
            }
        }
    }

    /**
     * Prints calendar.
     *
     * @param yearMonth Object containing month and year information.
     */
    public void printCalendar(YearMonth yearMonth, ArrayList<ArrayList<String[]>> calendar) {
        printCalendarLabel(yearMonth);
        printCalendarHeader();
        printCalendarBody(yearMonth, calendar);
    }

    /**
     * Prints calendar label.
     *
     * @param yearMonth Object containing month and year information.
     */
    public void printCalendarLabel(YearMonth yearMonth) {
        String[] months = {"", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST",
                "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        String month = months[yearMonth.getMonthValue()];
        String year = Integer.toString(yearMonth.getYear());
        System.out.println(month + " " + year);
    }

    /**
     * Prints calendar header.
     */
    public void printCalendarHeader() {
        System.out.println("-----------------------------------------------------------------------------------------"
                + "------------------------------------------------------------------");
        System.out.println("|         SUN         |         MON         |         TUE         |         WED         |"
                + "         THU         |         FRI         |         SAT         |");
        System.out.println("-----------------------------------------------------------------------------------------"
                + "------------------------------------------------------------------");
    }

    /**
     * Prints calendar body.
     *
     * @param yearMonth Object containing month and year information.
     */
    public void printCalendarBody(YearMonth yearMonth, ArrayList<ArrayList<String[]>> calendar) {
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
            int calendarRows = WEEKS_IN_MONTH;
            for (int day = 0; day < DAYS_IN_WEEK; day++) {
                if (!calendarDates.get(dayIndex + day).equals("  ")) {
                    int currentDay = Integer.parseInt(calendarDates.get(dayIndex + day).trim());
                    if (calendar.get(currentDay).size() > calendarRows) {
                        calendarRows = calendar.get(currentDay).size();
                    }
                }
                System.out.print(" " + calendarDates.get(dayIndex + day) + "                  |");
            }
            System.out.println();
            printCalendarDetails(calendar, calendarDates, dayIndex, calendarRows);
            System.out.println("-------------------------------------------------------------------------------------"
                    + "----------------------------------------------------------------------");
        }
    }

    /**
     * Prints calendar details.
     *
     * @param calendar      ArrayList containing task information for current month.
     * @param calendarDates ArrayList containing dates for current month.
     * @param dayIndex      Offset for current day.
     * @param calendarRows  Number of rows to be printed for current week.
     */
    public void printCalendarDetails(ArrayList<ArrayList<String[]>> calendar, ArrayList<String> calendarDates,
                                     int dayIndex, int calendarRows) {
        for (int row = 0; row < calendarRows; row++) {
            System.out.print("|");
            for (int day = 0; day < DAYS_IN_WEEK; day++) {
                String dayString = calendarDates.get(dayIndex + day).trim();
                if (dayString.equals("") || calendar.get(Integer.parseInt(dayString)).size() <= row) {
                    System.out.print("                     |");
                } else {
                    int currentDay = Integer.parseInt(dayString);
                    String taskTime = calendar.get(currentDay).get(row)[0];
                    String taskName = calendar.get(currentDay).get(row)[1];
                    if (taskTime.equals("")) {
                        printTodo(taskName);
                    } else {
                        printDeadlineAndEvent(taskTime, taskName);
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * Prints deadline and event details.
     *
     * @param taskTime Time of deadline or event.
     * @param taskName Name of deadline or event.
     */
    public void printDeadlineAndEvent(String taskTime, String taskName) {
        if (taskName.length() > TEXT_SIZE_SHORT) {
            taskName = taskName.substring(DESCRIPTION_SHORT_START, DESCRIPTION_SHORT_END);
            System.out.print(" " + taskTime + " " + taskName + ".. |");
        } else {
            taskName = String.format("%-" + TEXT_SIZE_SHORT + "s", taskName);
            System.out.print(" " + taskTime + " " + taskName + " |");
        }
    }

    /**
     * Prints todo details.
     *
     * @param taskName Name of todo.
     */
    public void printTodo(String taskName) {
        if (taskName.length() > TEXT_SIZE_LONG) {
            taskName = taskName.substring(DESCRIPTION_LONG_START, DESCRIPTION_LONG_END);
            System.out.print(" " + taskName + ".. |");
        } else {
            taskName = String.format("%-" + TEXT_SIZE_LONG + "s", taskName);
            System.out.print(" " + taskName + " |");
        }
    }

    /**
     * Prints header for free time slots.
     */
    public void printFree() {
        System.out.println("\t Here are your free time slots: ");
    }

    /**
     * Prints the free time slots that the user has.
     *
     * @param startDate The starting time of the free time slot.
     * @param endDate   The ending time of the free time slot.
     * @param count     The index of the free time slots.
     */
    public void printFreeTimes(String startDate, String endDate, int count) {
        System.out.println("\t" + count + ". " + startDate + " to " + endDate);
    }

    /**
     * Prints the new threshold that the user wants.
     *
     * @param threshold The threshold for upcoming deadlines requested by the user.
     */
    public void printUpdatedThreshold(String threshold) {
        System.out.println("\tThreshold has been updated to " + threshold);
    }
}
