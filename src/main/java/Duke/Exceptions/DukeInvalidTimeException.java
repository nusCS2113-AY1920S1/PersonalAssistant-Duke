package Duke.Exceptions;

public class DukeInvalidTimeException extends DukeException {
    @Override
    public String getMessage() {
        return super.getMessage() + "Invalid time and date format!";
    }
}
