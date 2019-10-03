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
        print("   ^____^\n"
                + "   ( oo )\\_______\n"
                + "   (____)\\       )\\/\\\n"
                + "         ||----w |\n"
                + "         ||     ||\n"
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
}
