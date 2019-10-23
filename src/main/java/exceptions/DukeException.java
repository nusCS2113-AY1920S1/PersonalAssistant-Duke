package exceptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class deals with all the exception messages.
 */
public class DukeException extends Exception {

    /**
     * to check exceptions for when user input command for schedule
     */
    public static void checkSchedule(String[] tokens) throws DukeException{
        if (tokens.length != 2){
            throw new DukeException("☹ OOPS!!! Invalid format. Please key in the command in the correct structure, e.g. schedule <date>");
        } else {
            checkDateFormat(tokens[1], "☹ OOPS!!! Invalid date. Please key in the command using valid date format, e.g. schedule 1/12/2019");
        }
    }

    /**
     * to check exceptions for when user input command for reminder
     */
    public static void checkReminder(String[] tokens) throws DukeException {
        if (tokens.length != 3) {
            throw new DukeException("☹ OOPS!!! Invalid format. Please key in the command in the correct structure, e.g. reminder <no. of reminders> <starting date>");
        } else {
            try {
                Integer.parseInt(tokens[1]);
            } catch (NumberFormatException e) {
                throw new DukeException("☹ OOPS!!! Invalid number. Please key in the command using numbers, e.g. reminder 3 <starting date>");
            }
            checkDateFormat(tokens[2], "☹ OOPS!!! Invalid date. Please key in the command using valid date format, e.g. reminder <no. of reminders> 1/12/2019");
        }
    }

    /**
     * to check whether date input by user is in right format for schedule and reminder
     */
    public static void checkDateFormat(String s, String e) throws DukeException {
        boolean isValid = true;
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("d/MM/yyyy");
            sourceFormat.parse(s);
        } catch (ParseException e1) {
            try {
                SimpleDateFormat sourceFormat = new SimpleDateFormat("d-MM-yyyy");
                sourceFormat.parse(s);
            } catch (ParseException e2) {
                isValid = false;
            }
        }
        if (!isValid) {
            throw new DukeException(e);
        }
    }

    public static final DukeException UNKNOWN_COMMAND = new DukeException("☹ OOPS!!! I'm sorry, "
        + "but I don't understand what you mean :-(");
    public static final DukeException EMPTY_TASK_IN_TODO = new DukeException("☹ OOPS!!! The "
        + "description of a todo cannot be empty, e.g. todo borrow book");
    public static final DukeException EMPTY_TASK_IN_DEADLINE = new DukeException("☹ OOPS!!! The "
        + "description of a deadline cannot be empty, e.g. deadline return book /by Sunday");
    public static final DukeException INVALID_FORMAT_IN_DEADLINE = new DukeException("☹ OOPS!!! Invalid "
        + "format. Please key in the duke.task in the correct structure, e.g. deadline return book /by Sunday");
    public static final DukeException EMPTY_TIME_IN_DEADLINE = new DukeException("☹ OOPS!!! Task "
        + "must have a valid deadline, e.g. deadline return book /by Sunday");
    public static final DukeException EMPTY_TASK_IN_EVENT = new DukeException("☹ OOPS!!! The description "
        + "of a event cannot be empty, e.g. event project meeting /at Mon 2-4pm");
    public static final DukeException EMPTY_TIME_IN_EVENT = new DukeException("☹ OOPS!!! Task "
        + "must have a valid time, e.g. event project meeting /at Mon 2-4pm");
    public static final DukeException INVALID_FORMAT_IN_EVENT = new DukeException("☹ OOPS!!! Invalid "
        + "format. Please key in the duke.task in the correct structure, e.g. event project meeting /at Mon 2-4pm");
    public static final DukeException TASK_DOES_NOT_EXIST = new DukeException("☹ OOPS!!! "
        + "Task does not exist.");
    public static final DukeException TASK_NO_MISSING = new DukeException("☹ OOPS!!! Please "
        + "provide a duke.task number.");
    public static final DukeException TASK_NO_MISSING_DELETE = new DukeException("☹ OOPS!!! Please "
        + "provide a duke.task number, e.g. delete 1");
    public static final DukeException TASK_NO_MISSING_FIND = new DukeException("☹ OOPS!!! Please "
        + "provide a keyword, e.g. find eat");
    public static final DukeException EMPTY_TASK_IN_DOAFTER = new DukeException("☹ OOPS!!! The "
        + "description of a 'do after' duke.task cannot be empty, e.g. do return book /after Sunday");
    public static final DukeException EMPTY_TIME_IN_PERIOD = new DukeException("☹ OOPS!!! Please "
        + "provide a valid time/duke.task for the 'period' duke.task, e.g. period return book between 20-05-1999 to 21-05-1999");
    public static final DukeException EMPTY_TIME_IN_DOAFTER = new DukeException("☹ OOPS!!! Please "
            + "provide a valid duration for the 'rdo after' duke.task, e.g. do return book /after Sunday");
    public static final DukeException EMPTY_TIME_IN_RECUR = new DukeException("☹ OOPS!!! Please "
            + "provide a valid duration for the 'recur' duke.task, e.g. recur return book /frequency Weekly");
    public static final DukeException EMPTY_TIME_IN_FIXED = new DukeException("☹ OOPS!!! Please "
            + "provide a valid duration for the 'fixed' duke.task, e.g. fixed return book /duration 2 hours");
    public static final DukeException INVALID_FORMAT_IN_DOAFTER = new DukeException("☹ OOPS!!! Invalid "
        + "format. Please key in the 'do after' duke.task in the correct format, e.g. do return book /after Sunday");

    public static final DukeException FILE_NOT_FOUND = new DukeException("☹ OOPS!!! Unknown error "
        + "extracting tasks from database.");
    public static final DukeException INPUT_NOT_FOUND = new DukeException("☹ OOPS!!! No user input found");
    public static final DukeException EMPTY_TASK_IN_EVENT_TENTATIVE = new DukeException("☹ OOPS!!! Please "
        + "provide a valid time/duke.task for the duke.task");
    public static final DukeException TASK_IS_NOT_TENTATIVE = new DukeException("☹ OOPS!!! Please select a "
        + "tentative duke.task.");
    public static final DukeException EMPTY_TASK_IN_RECUR = new DukeException("☹ OOPS!!! Please provide a valid "
        + "duke.task.");
    public static final DukeException EMPTY_TASK_IN_PERIOD = new DukeException("☹ OOPS!!! Please provide a valid "
            + "duke.task.");
    private String message;
    public static final DukeException EMPTY_TASK_IN_FIXED = new DukeException("☹ OOPS!!! Please provide a valid duke.task.");
    private DukeException(String message) {
        this.message = message;
    }

    public String getError() {
        return message;
    }

}