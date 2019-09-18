package exceptions;

/**
 * This class deals with all the exception messages.
 */
public class DukeException extends Exception {

    public static final DukeException UNKNOWN_COMMAND = new DukeException("☹ OOPS!!! I'm sorry, "
        + "but I don't understand what you mean :-(");
    public static final DukeException EMPTY_TASK_IN_TODO = new DukeException("☹ OOPS!!! The "
        + "description of a todo cannot be empty, e.g. todo borrow book");
    public static final DukeException EMPTY_TASK_IN_DEADLINE = new DukeException("☹ OOPS!!! The "
        + "description of a deadline cannot be empty, e.g. deadline return book /by Sunday");
    public static final DukeException INVALID_FORMAT_IN_DEADLINE = new DukeException("☹ OOPS!!! Invalid "
        + "format. Please key in the task in the correct structure, e.g. deadline return book /by Sunday");
    public static final DukeException EMPTY_TIME_IN_DEADLINE = new DukeException("☹ OOPS!!! Task "
        + "must have a valid deadline, e.g. deadline return book /by Sunday");
    public static final DukeException EMPTY_TASK_IN_EVENT = new DukeException("☹ OOPS!!! The description "
        + "of a event cannot be empty, e.g. event project meeting /at Mon 2-4pm");
    public static final DukeException EMPTY_TIME_IN_EVENT = new DukeException("☹ OOPS!!! Task "
        + "must have a valid time, e.g. event project meeting /at Mon 2-4pm");
    public static final DukeException INVALID_FORMAT_IN_EVENT = new DukeException("☹ OOPS!!! Invalid "
        + "format. Please key in the task in the correct structure, e.g. event project meeting /at Mon 2-4pm");
    public static final DukeException TASK_DOES_NOT_EXIST = new DukeException("☹ OOPS!!! "
        + "Task does not exist.");
    public static final DukeException TASK_NO_MISSING = new DukeException("☹ OOPS!!! Please "
        + "provide a task number.");
    public static final DukeException TASK_NO_MISSING_DELETE = new DukeException("☹ OOPS!!! Please "
        + "provide a task number, e.g. delete 1");
    public static final DukeException TASK_NO_MISSING_FIND = new DukeException("☹ OOPS!!! Please "
<<<<<<< HEAD
            + "provide a keyword, e.g. find eat");
=======
        + "provide a keyword, e.g. find eat");
    public static final DukeException EMPTY_TASK_IN_DOAFTER = new DukeException("☹ OOPS!!! The "
        + "description of a 'do after' task cannot be empty, e.g. do return book /after Sunday");
    public static final DukeException EMPTY_TIME_IN_DOAFTER = new DukeException("☹ OOPS!!! Please "
        + "provide a valid time/task for the 'do after' task, e.g. do return book /after Sunday");
    public static final DukeException INVALID_FORMAT_IN_DOAFTER = new DukeException("☹ OOPS!!! Invalid "
        + "format. Please key in the 'do after' task in the correct format, e.g. do return book /after Sunday");
>>>>>>> 29c893aa3eaff1fb2f275197b0fbf5076eeace72

    public static final DukeException FILE_NOT_FOUND = new DukeException("☹ OOPS!!! Unknown error "
        + "extracting tasks from database.");
    public static final DukeException INPUT_NOT_FOUND = new DukeException("☹ OOPS!!! No user input found");
    public static final DukeException EMPTY_TASK_IN_EVENT_TENTATIVE = new DukeException("☹ OOPS!!! Please "
        + "provide a valid time/task for the task");
    public static final DukeException TASK_IS_NOT_TENTATIVE = new DukeException("☹ OOPS!!! Please select a tentative task.");
    public static final DukeException EMPTY_TASK_IN_RECUR = new DukeException("☹ OOPS!!! Please provide a valid frequency.");
    private String message;

    private DukeException(String message) {
        this.message = message;
    }

    public String getError() {
        return message;
    }

}