package planner.exceptions.original;

public class ModTimeIntervalTooCloseException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "That time interval is too close!";
    }
}
