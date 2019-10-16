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
    public String printResponse() {
        return this.output;
    }

    /**
     * Prints the welcome message to the User.
     */
    public void showWelcome() {
        print("   \n"
                + "   ^____^________\n"
                + "   ( oo )\\ *  *  )\\/\\\n"
                + "   (____)||----w |  o \n"
                + "         ||     ||   00\n"
                + "   wmwwmWMWMwmWMmwMWWMWMwm\n"
                + "MOOOOOOOO!\n"

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
    public String confirmPrompt(String value) {
        System.out.println(value);
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

    /**
     * Prints a line to enclose message.
     */
    private void showLine() {
        System.out.println("______________________________________________________________________________");
    }

    /**
     * Prints out a message enclosed between two lines.
     * @param text message to be printed
     */
    private void print(String text) {
        showLine();
        System.out.println(text);
        showLine();
    }

    /**
     * Prints out when a new category is created.
     * @param categoryName name of the new category
     */
    public void showNewCategoryMessage(String categoryName) {
        print("Ok, I've added a new category named " + categoryName);
    }

    /**
     * Prompts the user to enter a category name.
     */
    void showAddCategoryMessage() {
        print("Please enter a name for your new category");
    }

    /**
     * Prints the list of categories.
     * @param categories list of current categories
     */
    public void showCategoryList(String categories) {
        print(categories);
    }
}
