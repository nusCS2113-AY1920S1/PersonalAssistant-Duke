package moomoo.task;

import java.util.Scanner;

/**
 * Represents the User Interface to be shown to the user.
 */
public class Ui {
    private String output;
    private Scanner inputScanner;

    /**
     * Returns the value to be printed to the GUI.
     * @return String to be printed on the GUI
     */
    public String printToGui() {
        return this.output;
    }

    /**
     * Prints the welcome message to the User.
     */
    public void showWelcome() {
        System.out.println("   ^____^\n"
                + "   (o  o)\\_______\n"
                + "   (____)\\       )\\/\\\n"
                + "         ||----w |\n"
                + "         ||     ||\n"
                + "MOOoOoOO!\n"
                + "Welcome to MooMooMoney! Your one-stop budgeting and expenses tracker!\n"
                + "What can MooMoo do for you today?");
    }

    /**
     * Used to read input from the user.
     * @return String representing the input given by the User
     */
    public String readCommand() {
        this.inputScanner = new Scanner(System.in);

        return this.inputScanner.nextLine();
    }

    /**
     * Sets good bye message to be shown to the User.
     */
    public void showGoodbye() {
        this.output = "Hope you had a great time using MooMooMoney!\n"
                + "See you next time :)";
    }

    /**
     * Returns message of MooMooException that occurs.
     * @param e MooMooException that occurs
     * @return Message of the MooMooException
     */
    public String printException(MooMooException e) {
        this.output = e.getMessage();
        return this.output;
    }

    /**
     * Prints out response from command.
     */
    public void showResponse() {
        System.out.println(this.output);
        this.output = "";
    }

    /**
     * Prompts the user for confirmation.
     * @return value given by user
     */
    public String confirmPrompt() {
        System.out.println("Are you sure you would like to make the change? (Y/N)");
        inputScanner = new Scanner(System.in);

        return inputScanner.nextLine();
    }

    /**
     * Sets the output to be printed.
     * @param output Input value to be printed.
     */
    public void setOutput(String output) {
        this.output = output;
    }
}
