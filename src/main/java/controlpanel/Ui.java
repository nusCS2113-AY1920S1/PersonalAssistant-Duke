package controlpanel;

import java.util.Scanner;

public class Ui {

    private Scanner scanner;
    private static String outputString = "";
    private static String graphContainerString = "";

    public Ui() {
        scanner = new Scanner(System. in);
        outputString = "";
        graphContainerString = "";
    }

    /**
     * The method to initialize and show welcome to the user.
     */
    public String showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String greeting = "____________________________________________________________\n"
                + "     Hello! I'm Duke\n"
                + "     What can I do for you?\n"
                + "____________________________________________________________\n";
        return ("Hello from\n" + logo + greeting);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public boolean inputStatus() {
        return scanner.hasNextLine();
    }

    public String showLine() {
        return ("____________________________________________________________\n");
    }

    public void showLoadingError() {
        System.out.println("This is not a valid input from the file!!!");
    }

    public String showError(String message) {
        return ("ERROR: " + message);
    }

    public void appendToOutput(String msg) {
        outputString += msg;
    }

    public String getOutputString() {
        return outputString;
    }

    public void clearOutputString() {
        outputString = "";
    }

    public String getGraphContainerString() {
        return graphContainerString;
    }

    public void clearGraphContainerString() {
        graphContainerString = "";
    }

    public void appendToGraphContainer(String msg) {
        graphContainerString += msg;
    }
}
