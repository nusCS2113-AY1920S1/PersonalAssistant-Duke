package ui;

import exceptions.DukeException;
import task.Tasks;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

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

    public void showWelcome() {
        System.out.println(space + "Hello from\n" + logo + "\n" + space + "Hello I am " + "Duke.");
        System.out.println(space + "What can I do for you?");
        System.out.println(line);
    }

    public void showUnknownCommand() {
        System.out.println(line);
        System.out.println(space + DukeException.UNKNOWN_COMMAND);
        System.out.println(line);
    }

    public static void showListIntroMessage() {
        System.out.println(line + "\n" + space + "Here are the tasks in your list:");
    }

    public static void showListTask(String type, String icon, String message, int num) {
        System.out.println(space + num + ".[" + type
            + "][" + icon
            + "] " + message);
    }

    public static void printLine() {
        System.out.println(line);
    }

    public static void showByeMessage() {
        System.out.println(line + "\n" + space + "Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    public static void showMarkAsDone(String type, String status, String message) {
        System.out.println(line + "\n" + space + "Nice! I've marked this task as done:");
        System.out.println(space + " [" + type
            + "][" + status
            + "] " + message);
        System.out.println(line);
    }

    public static void showError(String message) {
        System.out.println(line + "\n" + space + message + "\n" + line);
    }

    public static void showToDoSucess(String type, String status, String message, int todolist_number) {
        System.out.println(line + "\n" + space + "Got it. I've added this task:"
            + "\n" + space + " [" + type
            + "][" + status
            + "] " + message);

        if (todolist_number > 1) {
            System.out.println(space
                + "Now you have " + todolist_number + " tasks in the list.");
        } else {
            System.out.println(space + "Now you have " + todolist_number + " task in the list.");
        }
        System.out.println(line);
    }

    public static void showFindIntroMessage() {
        System.out.println(line + "\n" + space + "Here are the matching tasks in your list:");
    }

    public static void showDeleteMessage(String type, String status, String message, int num) {
        System.out.println(line + "\n" + space + "Noted. I've removed this task:"
            + "\n" + space + " [" + type
            + "][" + status
            + "] " + message);
        System.out.println(space + "Now you have " + num + " tasks in the list.");
        System.out.println(line);
    }

    public static void showFindTasks(String type, String status, String message, int num) {
        System.out.println(space + num + ".["
            + type + "][" + status
            + "] " + message);
    }

    public void showLoadingError() {
        System.out.println(line);
        System.out.println(space + "Existing database is empty!");
        System.out.println(line);
    }

    public String readInput() throws DukeException{
        try {
            String line = in.nextLine().trim();
            return line.trim();
        } catch (NoSuchElementException e) {
            return null;
        }

    }

    public static void showEventMessage (String status, String message, int num) {
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

    public static void showDeadlineMessage (String status, String message, int num) {
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
}
