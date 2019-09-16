package exception;

import ui.Ui;

/**
 * This Exception class is used to handle all of the expected exceptions and certain unexpected exceptions to give the
 * user a better understanding of why the program crashed.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class DukeException extends Exception {

    enum exceptionType {
        UNKNOWN_USER_COMMAND,
        EMPTY_USER_DESCRIPTION,
        EMPTY_DATE_OR_TIME,
        WRONG_DATE_OR_TIME,
        TASK_DOES_NOT_EXIST,
        UNABLE_TO_WRITE_FILE,
        UNABLE_TO_READ_FILE,
        TASK_CLASS_DOES_NOT_EXIST,
        FILE_DOES_NOT_EXIST
    }

    public static exceptionType inputType;

    public static String WRONG_DATE_OR_TIME() {
        Ui.printManual();
        Ui.printDash();
        return "☹ OOPS!!! The date or time of this add type command is not of the correct format. See the manual above!";
    }

    public static String TaskClash() {
        Ui.printDash();
        return "☹ OOPS!!! There is already a task scheduled at the same time, use view command to check your schedule "
                + "for the day";
    }

    public static String EMPTY_USER_DESCRIPTION() {
        Ui.printManual();
        Ui.printDash();
        return "☹ OOPS!!! The description of the command is missing. See the manual above!";
    }

    public static String UNKNOWN_USER_COMMAND() {
        Ui.printManual();
        Ui.printDash();
        return "☹ OOPS!!! The command you have entered is not of a valid type. See the manual above!";
    }

    public static String EMPTY_DATE_OR_TIME() {
        Ui.printManual();
        Ui.printDash();
        return "☹ OOPS!!! The date or time of this add type command is missing. See the manual above!";
    }

    public static String TASK_DOES_NOT_EXIST() {
        return "☹ OOPS!!! The task you searched for does not exist. See the manual above!";
    }

    public static String UNABLE_TO_WRITE_FILE() {
        return "☹ OOPS!!! Unable to write file. The program will be terminated.";
    }

    public static String UNABLE_TO_READ_FILE() {
        return "☹ OOPS!!! Unable to read from file. The program will be terminated.";
    }

    public static String CLASS_DOES_NOT_EXIST() {
        return "☹ OOPS!!! Unable to extract certain features of the Duke Project. Please ensure the project was imported properly";
    }

    public static String FILE_DOES_NOT_EXIST() {
        return "☹ OOPS!!! Unable to read from previous task list. A new file has been created for you";
    }

    public DukeException(String message) {
        super(message);
    }
}
