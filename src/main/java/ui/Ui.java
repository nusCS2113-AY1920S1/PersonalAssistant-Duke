package ui;

import java.util.Scanner;

/**
 * To deal with user interactions.
 */
public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showLoadingError() {
        System.out.println(":( OOPS!!! File path not found. Creating directory /data/data.txt");
    }

    public void showError(String error) {
        System.out.println(error);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showString(String string) {
        System.out.println(string);
    }

    /**
     * Show Welcome message on programme start.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        //System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Duke\n"
                + "What can I do for you?");
    }

    public void showLine() {
        System.out.println("    ____________________________________________________________");
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void indent() {
        System.out.print("    ");
    }
}
