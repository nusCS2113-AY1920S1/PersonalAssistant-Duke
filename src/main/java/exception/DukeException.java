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
    private static final String WRONG_FORMAT_MSG = "OOPS!!! The date or time of this add type command is not of the "
                                                    + "correct format. See the manual above!";
    private static final String TASK_CLASH_MSG = "OOPS!!! There is already a task scheduled at the same time, use view "
                                                    + "command to check your schedule for the day";
    private static final String MISSING_DESCRIPTION_MSG = "OOPS!!! The description of the command is missing. See "
                                                    + "the manual above!";
    private static final String UNKNOWN_COMMAND_MSG = "OOPS!!! The command you have entered is not of a valid type. " 
                                                    + "See the manual above!";
    private static final String MISSING_DATETIME_MSG = "OOPS!!! The date or time of this add type command is missing. "
                                                    + "See the manual above!";
    private static final String NONEXISTING_TASK_MSG = "OOPS!!! The task you searched for does not exist. " 
                                                    + "See the manual above!";
    private static final String WRITE_ERROR_MSG = "OOPS!!! Unable to write file. The program will be terminated.";
    private static final String READ_ERROR_MSG = "OOPS!!! Unable to read from file. The program will be terminated.";
    private static final String NONEXISTING_CLASS_MSG = "OOPS!!! Unable to extract certain features of the Duke Project"
                                                    + " Please ensure the project was imported properly";
    private static final String MISSING_FILE_MSG = "OOPS!!! Unable to read from previous task list. "
                                                    + "A new file has been created for you";
    private static final String MISSING_PRIORITY_MSG = "OOPS!!! The new priority level is missing. "
                                                    + "Please try typing the command again.";
    private static final String INAVLID_PRIORITY_MSG = "OOPS!!! The new priority level is invalid. "
                                                    + "It must be either high,medium or low.Please try again";
    private static final String INVALID_INDEX_MSG = "OOPS!!! The index given is invalid. "
                                                    + "It must be a existing index in the list. Please try again";
    private static final String INVALID_LOCATION_MSG = "OOPS!!! The location portion is not provided. Please try again";
    private static final String MISSING_COMMENT_MSG = "OOPS!!! The comment section is empty. Please try again";

    /**
     * Handles wrong date or time errors.
     *
     * @return message to be displayed
     */
    public static String wrongDateOrTime() {
        Ui.printManual();
        Ui.printDash();
        return WRONG_FORMAT_MSG;
    }

    /**
     * Handles if a new task clashes with a existing task.
     *
     * @return message to be displayed
     */
    public static String taskClash() {
        Ui.printDash();
        return TASK_CLASH_MSG;
    }

    /**
     * Handles empty task description errors.
     *
     * @return message to be displayed
     */
    public static String emptyUserDescription() {
        Ui.printManual();
        Ui.printDash();
        return MISSING_DESCRIPTION_MSG;
    }

    /**
     * Handles when parser does not understand input.
     *
     * @return message to be displayed
     */
    public static String unknownUserCommand() {
        Ui.printManual();
        Ui.printDash();
        return UNKNOWN_COMMAND_MSG;
    }

    /**
     * Handles empty date or time errors.
     *
     * @return message to be displayed
     */
    public static String emptyDateOrTime() {
        Ui.printManual();
        Ui.printDash();
        return MISSING_DATETIME_MSG;
    }

    /**
     * Handles if task searched does not exist.
     *
     * @return message to be displayed
     */
    public static String taskDoesNotExist() {
        return NONEXISTING_TASK_MSG;
    }

    /**
     * Handles if program is unable to save the tasks list to file.
     *
     * @return message to be displayed
     */
    public static String unableToWriteFile() {
        return WRITE_ERROR_MSG;
    }

    /**
     * Handles if program is unable to read an existing file for tasks list.
     *
     * @return message to be displayed
     */
    public static String unableToReadFile() {
        return READ_ERROR_MSG;
    }

    /**
     * Handles if class does not exists.
     *
     * @return message to be displayed
     */
    public static String classDoesNotExist() {
        return NONEXISTING_CLASS_MSG;
    }

    /**
     * Handles if file does not exists.
     *
     * @return message to be displayed
     */
    public static String fileDoesNotExist() {
        return MISSING_FILE_MSG;
    }

    /**
     * Handles if priority level is missing from priority command.
     *
     * @return message to be displayed
     */
    public static String emptyPriorityLevel() {
        return MISSING_PRIORITY_MSG;
    }

    /**
     * Handles if priority level is invalid.
     *
     * @return message to be displayed
     */
    public static String invalidPriorityLevel() {
        return INAVLID_PRIORITY_MSG;
    }

    /**
     * Handles if index is invalid.
     *
     * @return message to be displayed
     */
    public static String invalidIndex() {
        return INVALID_INDEX_MSG;
    }

    /**
     * notifies user if location is not provided.
     *
     * @return message to be displayed
     */
    public static String invalidLocation() {
        return INVALID_LOCATION_MSG;
    }

    /**
     * notifies user if comment is not provided.
     * 
     * @return message to be displayed
     */
    public static String emptyComment() {
        return MISSING_COMMENT_MSG;
    }

    public DukeException(String message) {
        super(message);
    }
}
