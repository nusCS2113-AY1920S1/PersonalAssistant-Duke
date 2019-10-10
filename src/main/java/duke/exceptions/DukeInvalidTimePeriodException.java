package duke.exceptions;

public class DukeInvalidTimePeriodException extends DukeException {
    public DukeInvalidTimePeriodException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "Invalid time period! " + this.getMessage();
    }
}
