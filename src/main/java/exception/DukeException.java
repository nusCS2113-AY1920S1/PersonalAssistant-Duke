package exception;

import ui.Ui;

/**
 * This Exception class is used to handle all of the expected exceptions and
 * certain unexpected exceptions to give the user a better understanding of why
 * the program crashed.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class DukeException extends Exception {

    /**
     * Handles wrong date or time errors.
     *
     * @return message to be displayed
     */
    public static String wrongDateOrTime() {
        Ui.printManual();
        Ui.printDash();
        return "☹ OOPS!!! The date or time of this add type command is not of the correct format."
            + "See the manual above!";
    }

    /**
     * Handles if a new task clashes with a existing task.
     *
     * @return message to be displayed
     */
    public static String taskClash() {
        Ui.printDash();
        return "☹ OOPS!!! There is already a task scheduled at the same time, use view command to check your schedule "
            + "for the day";
    }

    /**
     * Handles empty task description errors.
     *
     * @return message to be displayed
     */
    public static String emptyUserDescription() {
        Ui.printManual();
        Ui.printDash();
        return "☹ OOPS!!! The description of the command is missing. See the manual above!";
    }

    /**
     * Handles when parser does not understand input.
     *
     * @return message to be displayed
     */
    public static String unknownUserCommand() {
        Ui.printManual();
        Ui.printDash();
        return "☹ OOPS!!! The command you have entered is not of a valid type. See the manual above!";
    }

    /**
     * Handles empty date or time errors.
     *
     * @return message to be displayed
     */
    public static String emptyDateOrTime() {
        Ui.printManual();
        Ui.printDash();
        return "☹ OOPS!!! The date or time of this add type command is missing. See the manual above!";
    }

    /**
     * Handles if task searched does not exist.
     *
     * @return message to be displayed
     */
    public static String taskDoesNotExist() {
        return "☹ OOPS!!! The task you searched for does not exist. See the manual above!";
    }

    /**
     * Handles if program is unable to save the tasks list to file.
     *
     * @return message to be displayed
     */
    public static String unableToWriteFile() {
        return "☹ OOPS!!! Unable to write file. The program will be terminated.";
    }

    /**
     * Handles if program is unable to read an existing file for tasks list.
     *
     * @return message to be displayed
     */
    public static String unableToReadFile() {
        return "☹ OOPS!!! Unable to read from file. The program will be terminated.";
    }

    /**
     * Handles if class does not exists.
     *
     * @return message to be displayed
     */
    public static String classDoesNotExist() {
        return "☹ OOPS!!! Unable to extract certain features of the Duke Project"
            + "Please ensure the project was imported properly";
    }

    /**
     * Handles if file does not exists.
     *
     * @return message to be displayed
     */
    public static String fileDoesNotExist() {
        return "☹ OOPS!!! Unable to read from previous task list. A new file has been created for you";
    }

    /**
     * Handles if priority level is missing from priority command.
     *
     * @return message to be displayed
     */
    public static String emptyPriorityLevel() {
        return "☹ OOPS!!! The new priority level is missing. Please try typing the command again.";
    }

    /**
     * Handles if priority level is invalid.
     *
     * @return message to be displayed
     */
    public static String invalidPriorityLevel() {
        return "☹ OOPS!!! The new priority level is invalid. It must be either high,medium or low.Please try again";
    }

    /**
     * Handles if index is invalid.
     *
     * @return message to be displayed
     */
    public static String invalidIndex() {
        return "☹ OOPS!!! The index given is invalid. It must be a existing index in the list. Please try again";
    }

    public DukeException(String message) {
        super(message);
    }
}
