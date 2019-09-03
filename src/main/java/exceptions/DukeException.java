package exceptions;

public class DukeException extends Exception {

    public static final DukeException UNKNOWN_COMMAND = new DukeException("☹ OOPS!!! I'm sorry, "
            + "but I don't understand what you mean :-(");
    public static final DukeException EMPTY_TASK_IN_TODO = new DukeException(" ☹ OOPS!!! The "
            + "description of a todo cannot be empty, e.g. todo borrow book");
    public static final DukeException EMPTY_TASK_IN_DEADLINE = new DukeException(" ☹ OOPS!!! The "
            + "description of a deadline cannot be empty, e.g. deadline return book /by Sunday");
    public static final DukeException INVALID_FORMAT_IN_DEADLINE = new DukeException(" ☹ OOPS!!! Invalid "
            + "format. Please key in the task in the correct structure, e.g. deadline return book /by Sunday");
    public static final DukeException EMPTY_TIME_IN_DEADLINE = new DukeException("☹ OOPS!!! Task "
            + "must have a valid deadline, e.g. deadline return book /by Sunday");
    public static final DukeException EMPTY_TASK_IN_EVENT = new DukeException(" ☹ OOPS!!! The description "
            + "of a event cannot be empty, e.g. event project meeting /at Mon 2-4pm");
    public static final DukeException EMPTY_TIME_IN_EVENT = new DukeException(" ☹ OOPS!!! Task "
            + "must have a valid time, e.g. event project meeting /at Mon 2-4pm");
    public static final DukeException INVALID_FORMAT_IN_EVENT = new DukeException(" ☹ OOPS!!! Invalid "
            + "format. Please key in the task in the correct structure, e.g. event project meeting /at Mon 2-4pm");
    public static final DukeException TASK_DOES_NOT_EXIST = new DukeException("☹ OOPS!!! "
            + "Task does not exist.");
    public static final DukeException TASK_NO_MISSING = new DukeException("☹ OOPS!!! Please "
            + "provide a task number, e.g. done 1");
    public static final DukeException TASK_NO_MISSING_DELETE = new DukeException("☹ OOPS!!! Please "
            + "provide a task number, e.g. delete 1");
    public static final DukeException TASK_NO_MISSING_FIND = new DukeException("☹ OOPS!!! Please "
        + "provide a keyword, e.g. find eat");

    public static final DukeException FILE_NOT_FOUND = new DukeException("☹ OOPS!!! Unknown error "
            + "extracting tasks from database.");

    private String message;

    private DukeException(String message) {
        this.message = message;
    }

    public String getError() {
        return message;
    }


}