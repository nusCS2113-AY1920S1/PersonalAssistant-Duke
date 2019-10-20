package planner.exceptions;

public class ModInvalidTimePeriodException extends ModException {
    public ModInvalidTimePeriodException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "Invalid time period! " + this.getMessage();
    }
}
