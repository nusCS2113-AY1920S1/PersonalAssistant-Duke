package ui;

import exception.DukeException;

import java.util.Scanner;

/**
 * Represents the User Interface of Duke, and
 * manages both input and output operations.
 */
public class Ui {
    private Scanner dukeIn;

    /**
     * Constructs an Ui object.
     */
    public Ui() {
        dukeIn = new Scanner(System.in);
    }

    /**
     * Shows welcome message to the user when Duke starts.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("I am Duke. What can I do for you?");
    }

    /**
     * Reads one line of user's commands.
     *
     * @return User's command in <code>String</code> type.
     */
    public String readCommand() {
        return dukeIn.nextLine();
    }

    /**
     * Replaces the <code>System.out.println</code> method.
     *
     * @param s The string to be printed.
     */
    public void println(String s) {
        System.out.println(s);
    }

    /**
     * Prints the message of the exception.
     *
     * @param e the <code>DukeException</code> whose message will be printed.
     */
    public void showError(DukeException e) {
        System.out.println(e.getMessage());
    }
}
