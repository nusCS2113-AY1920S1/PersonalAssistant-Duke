package UI;

import java.util.Scanner;

/**
 * Method to handle the UI.UI.
 * Unlike the mainwindow and dialogbox classes, this one handles UI.UI elements of the terminal.
 * Mostly obsolete in favor of the abovementioned two classes.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class UI {

    /**
     * Constructor to for an UI.UI object of duke.
     */
    public UI() { //initialization

    }

    /**
     * Method to read in user input in the terminal and return the input to be parsed.
     * Does not take in inputs from the GUI.
     *
     * @return The user input as a string.
     */
    public static String inputCommand() { //read input and returns that input to be processed in main
        Scanner input = new Scanner(System.in);

        return input.nextLine();
    }

    /**
     * Method to return a bye message when the word bye is entered.
     * This signifies the end of the duke program.
     */
    public static void byeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Method to display a welcome message on startup of duke.
     * This signifies the start of the duke program.
     * Still has uses for the GUI and terminal.
     */
    public void welcomeMessage() {

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm JavaFX.Main.Duke"); //introduction
        System.out.println("What can I do for you?");

    }

}
