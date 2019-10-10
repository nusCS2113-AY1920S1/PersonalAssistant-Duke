package duke.exceptions;

public class DukeNoTimeException extends DukeException {
    @Override
    public String getMessage() {
        return super.getMessage() + "Cannot set time for this task!";
    }
}
