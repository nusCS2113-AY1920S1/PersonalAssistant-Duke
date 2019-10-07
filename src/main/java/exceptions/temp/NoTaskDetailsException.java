package exceptions.temp;

import exceptions.DukeException;

public class NoTaskDetailsException extends DukeException {

    public NoTaskDetailsException(String taskType) {
        super("☹ OOPS!!! The description of a " + taskType + " cannot be empty.");
    }
}
