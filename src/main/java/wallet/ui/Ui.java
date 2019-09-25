package wallet.ui;

import java.util.Scanner;

public class Ui {
    /**
     * Scanner object used for reading input from user.
     */
    private Scanner sc;

    /**
     * Constructs a new ui.Ui object.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints the welcome message of the program.
     */
    public void welcomeMsg() {
        String logo = "__             __       _   _         \n"
                + "\\ \\    __     / /      | | | |        _\n"
                + " \\ \\  /  \\   / /___,__ | | | | ______| |_    \n"
                + "  \\ \\/ /\\  \\/ /  [] | || | | |  []_\\_  __|\n"
                + "   \\__/  \\___/ \\__,_|_||_| |_|\\___/  |_|\n";
        System.out.println("Hello from\n" + logo);

        printLine();
        System.out.println("Hello! Welcome to the WalletCLi Application!");
        System.out.println("What can I do for you?");
        printLine();
    }

    /**
     * Prints the goodbye message when the user exits the program.
     */
    public void byeMsg() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Prompts the user for input.
     *
     * @return The input of the user.
     */
    public String readLine() {
        return sc.nextLine();
    }

    /**
     * Prints the separator lines for UI.
     */
    public void printLine() {
        System.out.println("____________________________________________________________");
    }
}
