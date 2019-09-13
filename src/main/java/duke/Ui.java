package duke;

import duke.constant.DukeResponse;

import java.util.Scanner;

/**
 * Reads in the command input by user amd
 * returns the response by the duke program.
 *
 */
public class Ui {

    /**
     * Stores the appropriate response based on users input command.
     * Once user command is processed, duke's response is stored using
     * setMessage method to store the appropriate message. Use
     * by showLine method to print out the response.
     */
    private String message;

    /**
     * A variable to access DukeResponse Class for all duke's response related
     * constants.
     */
    private DukeResponse dr = new DukeResponse();

    /**
     * Read in the command by user.
     * @return String input by user.
     */
    public String readCommand(Scanner sc) {

        return sc.nextLine();
    }

    /**
     * Set appropriate response by duke to be shown based on user's input.
     * @param message String duke will respond with based on user input.
     */
    public void setMessage(String message) {
        this.message = dr.SPACES + message + dr.SPACES;
    }

    /**
     * Prints out the greeting by duke program when duke starts running.
     * @return Greetings by duke
     */
    public String showWelcome() {
        return dr.LOGO + dr.SPACES + dr.GREET + dr.SPACES;
    }

    /**
     * Prints out the duke's response based on user's input.
     * @return Response by duke
     */
    public String showLine() {
        return this.message;
    }
}
