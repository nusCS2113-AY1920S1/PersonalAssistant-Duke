package planner.logic.exceptions.legacy;

public class ModInvalidTimePeriodException extends ModException {
    @Override
    public String getMessage() {
        return super.getMessage() + "Invalid time period! ";
    }
}
