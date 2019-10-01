package exceptions;

public class NoTaskDetailsException extends DukeException {

    public NoTaskDetailsException(String taskType) {
        super("☹ OOPS!!! The description of a " + taskType + " cannot be empty.");
    }
}
