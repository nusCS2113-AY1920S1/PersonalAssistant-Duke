package duke.exceptions;

public class DukeEmptyCommandException extends DukeException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Command cannot be empty!";
    }
}
