package duke;

import duke.tasks.RecurringTask;
import duke.tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class used to read the input duke.commands (for the command line implementation of duke.Duke) and
 * return the messages to be displayed in duke.Duke as a response to the user's input messages.
 */
public class Ui {

    private Scanner scanner = new Scanner(System.in);

    /**
     * Reads the input message of the user.
     *
     * @return the input message of the user
     */
    String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Returns a welcome message for duke.Duke.
     *
     * @return a welcome message for duke.Duke
     */
    String showWelcomeMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        return "Hello from\n" + logo + wrap("What can I do for you?\n");
    }

    /**
     * Returns a final message from duke.Duke in the case of termination.
     *
     * @return a terminal message from duke.Duke.
     */
    public static String showByeMessage() {
        return wrap("Bye. Hope to see you again soon!");
    }

    /**
     * Returns the error message associated with the caught duke.DukeException.
     *
     * @param e the duke.DukeException that was caught
     * @return the error message associated with the duke.DukeException
     */
    String showError(DukeException e) {
        return e.getMessage();
    }

    /**
     * Returns the String but wrapped in between two horizontal lines for enhanced
     * reading and display on the command line interface.
     *
     * @param content the String to be wrapped with horizontal lines
     * @return the wrapped String to be displayed
     */
    static String wrap(String content) {
        return ("\n__________________________________\n"
                + content
                + "\n__________________________________\n");
    }

    /**
     * Returns a String formatted for display that shows the results of the find command.
     *
     * @param list the results of the find command
     * @return the formatted String to be displayed
     */
    public String formatFind(ArrayList<Task> list) {
        StringBuilder result = new StringBuilder();
        if (list.size() == 0) {
            result.append("No such results!");
        } else {
            result.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < list.size(); i++) {
                result.append(i + 1)
                        .append(". ")
                        .append(list.get(i).toString());
                if (i != list.size() - 1) {
                    result.append("\n");
                }
            }
        }
        return wrap(result.toString());
    }

    /**
     * Returns a String formatted for display that shows all the elements in the task list
     * due to the list command.
     *
     * @param list the task list
     * @return the formatted String to be displayed
     */
    public String formatList(ArrayList<Task> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(i + 1)
                    .append(". ")
                    .append(list.get(i).toString());
            if (i != list.size() - 1) {
                result.append("\n");
            }
        }
        if (list.size() == 0) {
            result.append("The list is empty!");
        }
        return wrap(result.toString());
    }

    /**
     * Returns a String formatted for display that indicates that a task has been marked as done
     * by the done command.
     *
     * @param list the task list
     * @param index the index of the duke.tasks.Task object that was marked as done
     * @return the formatted String to be displayed
     */
    public String formatDone(ArrayList<Task> list, int index) {
        String result;
        if (list.get(index - 1) instanceof RecurringTask) {
            result = "Nice! I've marked this task as done:\n "
                    + ((RecurringTask)list.get(index - 1)).toOldString()
                    + "\n";
        } else {
            result = "Nice! I've marked this task as done:\n "
                    + list.get(index - 1).toString()
                    + "\n";

        }
        return wrap(result);
    }

    /**
     * Returns a String formatted for display that indicates that a task has been deleted by
     * the done command.
     *
     * @param list the task list prior to deletion
     * @param index the index of the item that was deleted
     * @return the formatted String to be displayed
     */
    public String formatDelete(ArrayList<Task> list,ArrayList<Task> newList, int index) {
        String word = (list.size() == 2) ? "task" : "tasks";
        String result = "Noted! I've removed this task:\n "
                + list.get(index - 1).toString()
                + "\n"
                + "Now you have "
                + (newList.size())
                + " "
                + word
                + " in the list.";
        return wrap(result);
    }

    /**
     * Returns a String formatted for display that indicates that a duke.tasks.Task object has been added
     * by the add command.
     *
     * @param list the task list
     * @param task the item that was added to the task list
     * @return the formatted String to be displayed
     */
    public String formatAdd(ArrayList<Task> list, Task task) {
        String word = (list.size() == 1) ? "task" : "tasks";
        String result = "Got it. I've added this task:\n  "
                + task.toString()
                + "\nNow you have "
                + list.size()
                + " "
                + word
                + " in the list.";
        return wrap(result);
    }

    /**
     * Returns a String for the items that need to be reminded from a list.
     *
     * @param list the task list.
     * @return the formatted String to be displayed.
     */
    public String formatReminder(ArrayList<Task> list) {
        if (list.size() == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append("The following tasks are due in 3 hours or less!\n\n");
        for (int i = 0; i < list.size(); i++) {
            result.append(i + 1)
                    .append(". ")
                    .append(list.get(i).toString());
            if (i != list.size() - 1) {
                result.append("\n");
            }
        }
        return wrap(result.toString());

    }
}
