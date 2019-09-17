package duke;

import java.util.Scanner;

/**
 * Manages the UI of Duke.
 * Prints intro and exit messages, and the standard newline.
 */

public class Ui {

    private Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    /**
     * Prints duke introduction message.
     */
    public void printIntro() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        printNewLine();
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Duke\n" + "What can I do for you?");
    }

    /**
     * Prints the standard newline.
     */
    public void printNewLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the duke exit message.
     */
    public void printExitMessage() {
        printNewLine();
        System.out.println("Bye! Hope to see you again soon!");
        printNewLine();
    }

    public String read() {
        return in.nextLine();
    }

}
