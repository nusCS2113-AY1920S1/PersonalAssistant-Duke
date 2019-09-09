package Duke;

import java.util.Scanner;

public class Ui {
    private static String border = "____________________________________________________________";

    /**
     * Constructor for Ui
     */
    public Ui () {

    }

    /**
     * Shows the welcome message when first launching Duke
     */
    public void showWelcome() {
        System.out.println(border);
        System.out.println("Hello! I'm Duke");
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);
        System.out.println("What can I do for you?");
        System.out.println(border);
    }

    /**
     * Prints a new border to separate messages by Ui
     */
    public void showLine() {
        System.out.println(border);
    }

    /**
     * Method to read command inputted by user
     * @return String containing input by user
     */
    public String readCommand() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Shows error when trying to load the save file
     */
    public void showLoadingError() {
        System.out.println("No saved files detected.");
    }

    /**
     * Displays the error message on the Ui
     * @param e String containing the error message
     */
    public void showError(String e) {
        System.out.println(e);
    }

    /**
     * Displays the message on the Ui
     * @param m String containing the message
     */
    public void showMessage(String m) {
        System.out.println(m);
    }
}
