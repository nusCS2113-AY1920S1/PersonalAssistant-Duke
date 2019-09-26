package duke.exceptions;

public class DukeTimeIntervalTooCloseException extends DukeException {

    @Override
    public String getMessage() {
        return super.getMessage() + "That time interval is too close!";
    }
}
