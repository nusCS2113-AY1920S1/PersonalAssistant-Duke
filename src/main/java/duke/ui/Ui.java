package duke.ui;

import parser.TimeParser;
import duke.task.Deadline;
import duke.task.TaskList;
import wrapper.Pair;
import duke.task.Event;
import duke.task.Tasks;
import wrapper.TimeInterval;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;


/**
 * This class handles user's input and do the required appropriate actions.
 */
public class Ui {

    protected static String logo =
        " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    protected static String line = "   ____________________________________________________________";
    protected static String space = "    ";
    private Scanner in;

    public Ui() {
        in = new Scanner(System.in);
    }


    /**
     * Prints the confirmation message when a doAfter duke.task had been saved into the database.
     */

    public static void showDoAfterMessage(String status, String newtodoTask1, int i) {
        System.out.println(line + "\n" + space + "Got it. I've added this duke.task:" + "\n" + space + " [A]["
            + status
            + "] " + newtodoTask1);
        if (i > 1) {
            System.out.println(space + "Now you have " + i + " tasks in the list.");
        } else {
            System.out.println(space + "Now you have " + i + " duke.task in the list.");
        }
        System.out.println(line);

    }


    /**
     * Prints the welcome message at the start when user opens the duke application.
     */

    public void showWelcome() {
        System.out.println(space + "Hello from\n" + logo + "\n" + space + "Hello I am " + "duke.Duke.");
        System.out.println(space + "What can I do for you?");
        System.out.println(line);
    }


    /**
     * Prints a message before the application lists out each respective duke.task in database.
     */

    public static void showListIntroMessage() {
        System.out.println(line + "\n" + space + "Here are the tasks in your list:");
    }


    /**
     * Prints a respective duke.task when user enters list.
     */

    public static void showListTask(String type, String icon, String message, int num) {
       System.out.println(space + num + ".[" + type
            + "][" + icon
            + "] " + message);
    }

    public static void printLine() {
        System.out.println(line);
    }


    /**
     * Prints a message once user enters bye.
     */

    public static void showByeMessage() {
        System.out.println(line + "\n" + space + "Bye. Hope to see you again soon!");
        System.out.println(line);
    }


    /**
     * Prints a message once a duke.task has been done and updated into database.
     */

    public static void showMarkAsDone(String type, String status, String message) {
        System.out.println(line + "\n" + space + "Nice! I've marked this duke.task as done:");
        System.out.println(space + " [" + type
            + "][" + status
            + "] " + message);
        System.out.println(line);
    }


    /**
     * Prints a message when a particular duke.task has been marked as done by user.
     */

    public static void showMarkAsUnDone(String type, String status, String message) {
        System.out.println(line + "\n" + space + "Nice! I've unmarked this duke.task as done:");
        System.out.println(space + " [" + type
            + "][" + status
            + "] " + message);
        System.out.println(line);
    }

    public static void showError(String message) {
        System.out.println(line + "\n" + space + message + "\n" + line);
    }

    public static void showMsg(String message) {
        System.out.println(line + "\n" + space + message + "\n" + line);
    }


    /**
     * Prints a message once a To-Do duke.task has been successfully added into database.
     */

    public static void showToDoSucess(String type, String status, String message, int todolistNumber) {
        System.out.println(line + "\n" + space + "Got it. I've added this duke.task:"
            + "\n" + space + " [" + type
            + "][" + status
            + "] " + message);

        if (todolistNumber > 1) {
            System.out.println(space
                + "Now you have " + todolistNumber + " tasks in the list.");
        } else {
            System.out.println(space + "Now you have " + todolistNumber + " duke.task in the list.");
        }
        System.out.println(line);
    }

    public static void showFindIntroMessage() {
        System.out.println(line + "\n" + space + "Here are the matching tasks in your list:");
    }


    /**
     * Prints a message once a duke.task has been deleted from database.
     */

    public static void showDeleteMessage(String type, String status, String message, int num) {
        System.out.println(line + "\n" + space + "Noted. I've removed this duke.task:"
            + "\n" + space + " [" + type
            + "][" + status
            + "] " + message);
        System.out.println(space + "Now you have " + num + " tasks in the list.");
        System.out.println(line);
    }


    /**
     * Prints a respective duke.task when user enter find.
     */

    public static void showFindTasks(String type, String status, String message, int num) {
        System.out.println(space + num + ".["
            + type + "][" + status
            + "] " + message);
    }


    /**
     * Prints a error message when database is empty.
     */

    public void showLoadingError() {
        System.out.println(line);
        System.out.println(space + "Existing database is empty!");
        System.out.println(line);
    }


    /**
     * Reads users' input.
     * Returns these input.
     */

    public String readInput() {
        String line = in.nextLine().trim();
        return line.trim();

    }


    /**
     * Prints a message to query users for dates when user want to add a tentative duke.task.
     */

    public static void queryForDates() {
        System.out.println(line + "\n" + space + "Please provide the tentative dates.");
        System.out.println(line);
    }


    /**
     * Prints the opening message for tentative event schedule.
     */

    public static void showEventTentativeOpeningMessage() {
        System.out.println(line + "\n" + space
            + "Got it. I've added the following tasks to be tentatively scheduled :");
    }


    /**
     * Prints a message once a event duke.task has been successfully added into database.
     */

    public static void showEventTentativeMessage(String type, String status, String message) {
        System.out.println(space + "["
            + type + "][" + status + "] " + message);
    }


    /**
     * Prints the closing message after user have added tentative event.
     *
     * @param num1 which is the total number of tasks.
     * @param num2 which is the total number of tentative only tasks.
     */

    public static void showEventTentativeCloseMessage(int num1, int num2) {
        System.out.println(line);
        System.out.println(space + "Now you have " + num1 + " tasks in the list including "
            + num2 + " tentative tasks");
        System.out.println(line);
    }


    /**
     * Prints a message once a tentative event duke.task has been successfully added into database.
     */

    public static void showEventMessage(String type, String status, String message, int num) {
        System.out.println(line + "\n" + space + "Got it. I've added this duke.task:" + "\n" + space + "["
            + type + "][" + status
            + "] " + message);
        if (num > 1) {
            System.out.println(space + "Now you have " + num + " tasks in the list.");
        } else {
            System.out.println(space + "Now you have " + num + " duke.task in the list.");
        }
        System.out.println(line);
    }


    /**
     * Prints a message once a deadline duke.task has been successfully added into database.
     */
    public static void showDeadlineMessage(String status, String message, int num) {
        System.out.println(line + "\n" + space + "Got it. I've added this duke.task:" + "\n" + space + " [D]["
            + status
            + "] " + message);
        if (num > 1) {
            System.out.println(space + "Now you have " + num + " tasks in the list.");
        } else {
            System.out.println(space + "Now you have " + num + " duke.task in the list.");
        }
        System.out.println(line);

    }


    /**
     * This function prints a message for free time.
     */

    public static void showFreeTime(int num, TimeInterval freeslot) {
        System.out.println(line + "\n" + space + "You have your free time that is at least "
            + Integer.toString(num) + " hours at these interval");

        if (freeslot.getStartDate().equals(freeslot.getEndDate())) {
            System.out.println(space + freeslot.getStartDate() + " onwards");
        } else {
            System.out.println(space + freeslot.getStartDate() + " to " + freeslot.getEndDate());
        }
        System.out.println(line);
    }

    /**
     * This function prints message for update time.
     */
    public static void updateTime(Tasks temp) {
        System.out.println(line + "\n" + space + "Great you have updated the time for the following duke.task!");

        if (temp.getType().equals("E")) {
            System.out.println(space + ((Event) temp).toMessage());


        } else if (temp.getType().equals("D")) {
            System.out.println(space + ((Deadline) temp).toMessage());
        }
        System.out.println(line);
    }

    /**
     * This message prints message for conflicts.
     */
    public static void showConflicts(ArrayList<Pair> conflicts) {

        System.out.println(line + "\n");

        if (conflicts.size() == 0) {
            System.out.println(space + "Nice! you do not have any conflicts!!");
        } else {
            System.out.println(space + "Oh No!! you have the following conflicts!!");
            int i = 1;
            for (Pair e : conflicts) {
                Tasks t1 = e.getTask1();
                Tasks t2 = e.getTask2();
                System.out.println(space + (i++) + ". " + t1.getDescription() + " & " + t2.getDescription());

            }
        }

        System.out.println(line);
    }

    /**
     * Prints a message corresponding to the user request for schedule
     */
    public static void showScheduleIntroMessage(String s) {
        System.out.println(line + "\n" + space + "Here is ur schedule for " + s + ":");
    }

    /**
     * Prints the total number of tasks in the schedule
     */
    public static void showScheduleFinalMessage(int count) {
        if (count == 2) {
            System.out.println("\n" + space + space + "*there is a total of 1 duke.task scheduled*");
        } else {
            System.out.println("\n" + space + space + "*there is a total of " + --count + " tasks scheduled*");
        }
    }

    /**
     * Prints the duke.task according to the schedule format
     */
    public static void printScheduleTask(Map.Entry<Date, Tasks> task){
        if (task.getValue().getType().equals("E")){
            System.out.println(space + TimeParser.getStringTime(task.getKey()) + "\tevent: " + task.getValue().parseDescription());
        } else{
            System.out.println(space + TimeParser.getStringTime(task.getKey()) + "\tdeadline: " + task.getValue().parseDescription());
        }
    }

    /**
     * Prints a message corresponding to the user request for reminder
     */
    public static void showReminderIntroMessage(int n, String date) {
        if (TaskList.getTreeMap().isEmpty()) {
            System.out.println(line + "\n" + space + "You have no upcoming tasks");
        } else {
            System.out.println(line + "\n" + space + "Here is a reminder for your next " + n + " upcoming tasks from " + date);
        }
    }

    /**
     * Prints a notice to tell the user when there is no upcoming tasks to be reminded of
     */
    public static void showEmptyReminderMessage(int count){
        if (count == 1){
            System.out.println(space + space + "*You have no upcoming tasks*");
        }
    }

    /**
     * Prints the duke.task according to the reminder format
     */
    public static void printReminder(Map.Entry<Date, Tasks> log, int count){
        System.out.println(space + count + ". " + log.getValue().getDescription());
    }

    public static void printScheduleDate(String date){
        System.out.println(space + date + ":");
    }
}