package planner.exceptions;

public class ModTimeIntervalTooCloseException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "That time interval is too close!";
    }
}
