package optix.ui;

import java.util.Scanner;

/**
 * Reads in the input user and
 * returns the response by Optix.
 */
public class Ui {

    /**
     * Stores the appropriate response based on users input command.
     * Once user command is processed, Optix's response is stored using
     * setMessage method to store the appropriate message. Use
     * by showCommandLine method to print out the response.
     */
    private String message;

    private Scanner sc = new Scanner(System.in);

    private static final String SPACES = "__________________________________________________________________________________\n";

    private static final String MESSAGE_GREET = "Hello! I'm Optix\n"
            + "What can I do for you?\n";


    public Ui() {
        this.message = MESSAGE_GREET;
    }

    /**
     * Read in the command by user.
     *
     * @return String input by user.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Set appropriate response by Optix to be shown based on user's input.
     *
     * @param message String Optix will respond with based on user input.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Prints out the Optix's response based on user's input.
     *
     * @return Response by Optix.
     */
    public String showCommandLine() {
        return SPACES + message + SPACES;
    }

    public void exitOptix() {
        sc.close();
    }
}
