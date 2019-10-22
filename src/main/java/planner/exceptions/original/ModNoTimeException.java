package planner.exceptions.original;

public class ModNoTimeException extends ModException {
    @Override
    public String getMessage() {
        return super.getMessage() + "Cannot set time for this task!";
    }
}
