import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Handles the taking in of input from the user and passes it to the Parser to translate it into usable commands.
 * Also handles printing of messages for the user's reading.
 */

public class Ui {
    protected Scanner inputScanner;
    protected String input;

    /**
     * Constructor of the Ui. Initializes the scanner to take in user input,
     * and prints the Duke welcome message.
     */
    public Ui() {
        inputScanner = new Scanner(System.in);
        welcome();
    }

    /**
     * Obtains a new input from the user.
     * @return the new input typed by the user.
     */
    public String getInput() {
        input = inputScanner.nextLine();
        return input;
    }

    /**
     * Prints an array of non-Task Strings in the required Duke format.
     * @param inputs an array of Strings to be printed.
     */
    public void printArray(ArrayList<String> inputs) {
        System.out.println("\t____________________________________________________________");
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println("\t" + inputs.get(i));
        }
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Prints a message, followed by an array of Tasks in String format.
     * @param command the desired message
     * @param inputs the array of Tasks in String format.
     */
    public void printTaskArray(String command, ArrayList<String> inputs) {
        System.out.println("\t____________________________________________________________");
        System.out.println("\t" + command);
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println("\t" + inputs.get(i));
        }
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Prints a single String in the required Duke format.
     * @param input the String to be printed.
     */
    public void print(String input) {
        printArray(new ArrayList<String>(Arrays.asList(input)));
    }

    /**
     * Prints a message, followed by a single Task in String format.
     * @param command the desired message
     * @param input the Task, in String format.
     */
    public void printTask(String command, String input) {
        printTaskArray(command, new ArrayList<String>(Arrays.asList(input)));
    }

    /**
     * Prints a message stating that a Task has been added.
     * @param input the Task that has been added, in String format.
     * @param noTasks the number of Tasks that now exist.
     */
    public void printAddedTask(String input, int noTasks) {
        System.out.println("\t____________________________________________________________");
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + input);
        System.out.println("\tNow you have " + noTasks + " tasks in the list.");
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Prints a message stating that a Task has been removed.
     * @param input the Task that has been removed, in String format.
     * @param noTasks the number of Tasks that now exist.
     */
    public void printRemovedTask(String input, int noTasks) {
        System.out.println("\t____________________________________________________________");
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t  " + input);
        System.out.println("\tNow you have " + noTasks + " tasks in the list.");
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Prints the farewell message when Duke is closed.
     */
    public void farewell() {
        print("Bye. Hope to see you again soon!");
    }

    /**
     * Prints a welcome message when Duke is started up.
     */
    public void welcome() {
        printArray(new ArrayList<String>(Arrays.asList("Hello! I'm Duke", "What can I do for you?")));
    }

}