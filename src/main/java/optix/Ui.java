package optix;

import optix.constant.OptixResponse;

import java.util.Scanner;

/**
 * Reads in the command input by user amd
 * returns the response by the Optix program.
 *
 */
public class Ui {

    /**
     * Stores the appropriate response based on users input command.
     * Once user command is processed, Optix's response is stored using
     * setMessage method to store the appropriate message. Use
     * by showLine method to print out the response.
     */
    private String message;

    /**
     * A variable to access DukeResponse Class for all Optix's response related
     * constants.
     */
    private OptixResponse or = new OptixResponse();

    private Scanner sc = new Scanner(System.in);

    /**
     * Read in the command by user.
     * @return String input by user.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Set appropriate response by Optix to be shown based on user's input.
     * @param message String Optix will respond with based on user input.
     */
    public void setMessage(String message) {
        this.message = or.SPACES + message + or.SPACES;
    }

    /**
     * Prints out the greeting by Optix program when Optix starts running.
     * @return Greetings by Optix
     */
    public String showWelcome() {
        return or.SPACES + or.GREET + or.SPACES;
    }

    /**
     * Prints out the Optix's response based on user's input.
     * @return Response by Optix
     */
    public String showLine() {
        return this.message;
    }

    public void exitOptix() {
        sc.close();
    }
}
