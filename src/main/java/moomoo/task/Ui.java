package moomoo.task;

import java.util.Scanner;

/**
 * Represents the User Interface to be shown to the user.
 */
public class Ui {
    public String output;
    private Scanner inputScanner;

    /**
     * Returns the value to be printed to the GUI.
     * @return String to be printed on the GUI
     */
    public String printToGui() {
        return output;
    }

    /**
     * Prints the welcome message to the User.
     */
    public void showWelcome() {
        System.out.println("   ^____^\n"
                + "   ( oo )\\_______\n"
                + "   (____)\\       )\\/\\\n"
                + "         ||----w |\n"
                + "         ||     ||\n"
                + "MOOOOOOOO\n"
                + "Welcome to MooMooMoney! Your one-stop budgeting and expenses tracker!\n"
                + "What can MooMoo do for you today?");
    }

    /**
     * Used to read input from the user.
     * @return String representing the input given by the User
     */
    public String readCommand() {
        inputScanner = new Scanner(System.in);

        return inputScanner.nextLine();
    }

    /**
     * Returns good bye message to be shown to the User.
     * @return String representing a good bye message when "bye" command is given
     */
    public String showGoodbye() {
        return ("Hope you had a great time using MooMooMoney!\n"
                + "See you next time :)");
    }

    /**
     * Prints out the loading error when no file could be found.
     */
    public void showLoadingError() {
        System.out.println("OOPS!!! File not found or is empty. Creating a new task list!");
    }

    /**
     * Returns message of MooMooException that occurs.
     * @param e MooMooException that occurs
     * @return Message of the MooMooException
     */
    public String printException(MooMooException e) {
        output = e.getMessage();
        return output;
    }

    /**
     * Prints out response from command.
     */
    public void showResponse() {
        System.out.println(this.output);
        output = "";
    }


}
