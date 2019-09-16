package ui;

import wrapper.Pair;
import task.Event;
import task.Tasks;
import wrapper.TimeInterval;

import java.util.ArrayList;
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
     * Prints the welcome message at the start when user opens the duke application.
     */
    public void showWelcome() {
        System.out.println(space + "Hello from\n" + logo + "\n" + space + "Hello I am " + "Duke.");
        System.out.println(space + "What can I do for you?");
        System.out.println(line);
    }

    /**
     * Prints a message before the application lists out each respective task in database.
     */
    public static void showListIntroMessage() {
        System.out.println(line + "\n" + space + "Here are the tasks in your list:");
    }

    /**
     * Prints a respective task when user enters list.
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
     * Prints a message once a task has been done and updated into database.
     */
    public static void showMarkAsDone(String type, String status, String message) {
        System.out.println(line + "\n" + space + "Nice! I've marked this task as done:");
        System.out.println(space + " [" + type
            + "][" + status
            + "] " + message);
        System.out.println(line);
    }

    /**
     * Prints a message when a particular task has been marked as done by user.
     */
    public static void showMarkAsUnDone(String type, String status, String message) {
        System.out.println(line + "\n" + space + "Nice! I've unmarked this task as done:");
        System.out.println(space + " [" + type
            + "][" + status
            + "] " + message);
        System.out.println(line);
    }

    public static void showError(String message) {
        System.out.println(line + "\n" + space + message + "\n" + line);
    }

    /**
     * Prints a message once a To-Do task has been successfully added into database.
     */
    public static void showToDoSucess(String type, String status, String message, int todolistNumber) {
        System.out.println(line + "\n" + space + "Got it. I've added this task:"
            + "\n" + space + " [" + type
            + "][" + status
            + "] " + message);

        if (todolistNumber > 1) {
            System.out.println(space
                + "Now you have " + todolistNumber + " tasks in the list.");
        } else {
            System.out.println(space + "Now you have " + todolistNumber + " task in the list.");
        }
        System.out.println(line);
    }

    public static void showFindIntroMessage() {
        System.out.println(line + "\n" + space + "Here are the matching tasks in your list:");
    }

    /**
     * Prints a message once a task has been deleted from database.
     */
    public static void showDeleteMessage(String type, String status, String message, int num) {
        System.out.println(line + "\n" + space + "Noted. I've removed this task:"
            + "\n" + space + " [" + type
            + "][" + status
            + "] " + message);
        System.out.println(space + "Now you have " + num + " tasks in the list.");
        System.out.println(line);
    }

    /**
     * Prints a respective task when user enter find.
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
     * Prints a message once a event task has been successfully added into database.
     */
    public static void showEventMessage(String status, String message, int num) {
        System.out.println(line + "\n" + space + "Got it. I've added this task:" + "\n" + space + " [E]["
            + status
            + "] " + message);
        if (num > 1) {
            System.out.println(space + "Now you have " + num + " tasks in the list.");
        } else {
            System.out.println(space + "Now you have " + num + " task in the list.");
        }
        System.out.println(line);
    }

    /**
     * Prints a message once a deadline task has been successfully added into database.
     */
    public static void showDeadlineMessage(String status, String message, int num) {
        System.out.println(line + "\n" + space + "Got it. I've added this task:" + "\n" + space + " [D]["
            + status
            + "] " + message);
        if (num > 1) {
            System.out.println(space + "Now you have " + num + " tasks in the list.");
        } else {
            System.out.println(space + "Now you have " + num + " task in the list.");
        }
        System.out.println(line);
    }

    public static void showFreeTime(int num ,TimeInterval freeslot ) {
        System.out.println(line + "\n" + space + "You have your free time that is at least " + Integer.toString(num)+ " hours at these interval");

        if(freeslot.getStartDate().equals(freeslot.getEndDate())){
            System.out.println(space+ freeslot.getStartDate() + " onwards");
        }else{
            System.out.println(space + freeslot.getStartDate() + " to " + freeslot.getEndDate());
        }
        System.out.println(line);
    }

    public static void showConflicts(ArrayList<Pair> conflicts) {

        System.out.println(line + "\n");

        if(conflicts.size() == 0){
            System.out.println(space + "Nice! you do not have any conflicts!!");
        }else{
            System.out.println(space + "Oh No!! you have the following conflicts!!");
            int i =1;
            for(Pair e : conflicts){
                Tasks t1 = e.getTask1();
                Tasks t2 = e.getTask2();
                System.out.println(space + (i++) + ". "+ t1.getDescription() + " & " + t2.getDescription());

            }
        }

        System.out.println(line);
    }
}
