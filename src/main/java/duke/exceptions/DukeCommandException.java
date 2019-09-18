package duke.exceptions;

public class DukeCommandException extends DukeException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Must be a valid command!";
    }
}
