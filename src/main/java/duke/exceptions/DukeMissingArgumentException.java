package duke.exceptions;

public class DukeMissingArgumentException extends DukeException {

    public DukeMissingArgumentException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "Missing " + this.getMessage();
    }
}
