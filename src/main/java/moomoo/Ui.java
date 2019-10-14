package moomoo;

import java.util.Scanner;

public class Ui {

    private Scanner input;

    public Ui() {
        input = new Scanner(System.in);
    }

    /**
     * Shows welcome message.
     */
    public void showWelcome() {
        print("   \n"
                + "   ^____^________\n"
                + "   ( oo )\\ *  *  )\\/\\\n"
                + "   (____)||----w |  o \n"
                + "         ||     ||   00\n"
                + "   wmwwmWMWMwmWMmwMWWMWMwm\n"
                + "MOOOOOOOO\n"
                + "Welcome to MooMooMoney! Your one-stop budgeting and expenses tracker!\n"
                + "What can MooMoo do for you today?");
    }

    private void showLine() {
        System.out.println("______________________________________________________________________________");
    }

    public void showGoodbye() {
        print("Hope you had a great time using MooMooMoney!\n"
                + "See you next time :)");
    }

    public String readCommand() {
        return input.nextLine();
    }

    private void print(String text) {
        showLine();
        System.out.println(text);
        showLine();
    }

    public void showErrorMessage(String message) {
        print(message);
    }

    void showNewCategoryMessage(String categoryName) {
        print("Ok, I've added a new category named " + categoryName);
    }

    void showAddCategoryMessage() {
        print("Please enter a name for your new category");
    }

    public void showCategoryList(String categories) {
        print(categories);
    }
}
