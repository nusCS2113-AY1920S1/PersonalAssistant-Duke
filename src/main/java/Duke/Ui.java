package Duke;

import Duke.Constant.Duke_Response;

import java.util.Scanner;

/**
 * Reads in the command input by user amd
 * returns the response by the Duke program.
 *
 */
public class Ui {

    /**
     * Stores the appropriate response based on users input command.
     * Once user command is processed, Duke's response is stored using
     * setMessage method to store the appropriate message. Use
     * by showLine method to print out the response.
     */
    private String message;

    /**
     * A variable to access Duke_Response Class for all Duke's response related
     * constants.
     */
    private Duke_Response dr = new Duke_Response();


    /**
     * Read in the command by user.
     * @return String input by user.
     */
    public String readCommand(Scanner sc){

        return sc.nextLine();
    }

    /**
     * Set appropriate response by Duke to be shown based on user's input.
     * @param message the String that Duke will respond with based on user input.
     */
    public void setMessage(String message) {
        this.message = dr.SPACES + message + dr.SPACES;
    }

    /**
     * Prints out the greeting by Duke program when Duke starts running.
     */
    public void showWelcome(){
        System.out.println(dr.LOGO+ dr.SPACES + dr.GREET + dr.SPACES);
    }

    /**
     * Prints out the Duke's response based on user's input.
     */
    public String showLine(){
        return this.message;
    }
}
