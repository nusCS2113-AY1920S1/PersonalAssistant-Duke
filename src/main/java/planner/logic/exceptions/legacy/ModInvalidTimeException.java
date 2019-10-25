package planner.logic.exceptions.legacy;

public class ModInvalidTimeException extends ModException {
    @Override
    public String getMessage() {
        return super.getMessage() + "Invalid time and date format!";
    }
}
