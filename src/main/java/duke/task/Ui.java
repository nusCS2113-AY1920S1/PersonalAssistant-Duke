package duke.task;

import java.util.Scanner;

/**
 * Represents the User Interface to be shown to the user.
 */
public class Ui {
    public String output;

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
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo + "Hello! I'm Duke\nWhat can I do for you?");

    }

    /**
     * Used to read input from the user.
     * @return String representing the input given by the User
     */
    public String readCommand() {
        String input;
        Scanner inputScanner = new Scanner(System.in);

        return inputScanner.nextLine();
    }

    /**
     * Returns good bye message to be shown to the User.
     * @return String representing a good bye message when "bye" command is given
     */
    public String showGoodByeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Prints out the loading error when no file could be found.
     */
    public void showLoadingError() {
        System.out.println("OOPS!!! File not found or is empty. Creating a new task list!");
    }

    /**
     * Prints out any error that occurs.
     * @param s Error message to be printed
     */
    public void showError(String s) {
        System.out.println("Error: " + s);
    }

    /**
     * Returns message of duke.task.DukeException that occurs.
     * @param e duke.task.DukeException that occurs
     * @return Message of the duke.task.DukeException
     */
    public String printException(DukeException e) {
        return e.getMessage();
    }

    /**
     * Prints out response from command.
     */
    public void showResponse() {
        System.out.println(this.output);
    }

    /**
     * Returns a formatted list of tasks when "list" command is given.
     * @return String that represents the list of task that is formatted to be shown to the User
     */
    public String printList() {
        int taskListSize = TaskList.getSize();

        if (TaskList.getSize() == 0) {
            return "You have no tasks in your list";
        }
        int start = 1;
        String outputString = "";
        outputString += "Here are the tasks in your list:\n";

        for (int i = 0; i < taskListSize; ++i) {
            if (start == taskListSize) {
                outputString += start + "." + TaskList.getTask(i).toString();
            } else {
                outputString += start + "." + TaskList.getTask(i).toString() + '\n';
                start++;
            }
        }
        return outputString;
    }

}
