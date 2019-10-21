package planner.exceptions.original;

public class ModMultipleValuesForSameArgumentException extends ModException {
    @Override
    public String getMessage() {
        return super.getMessage() + "Cannot set multiple values for same argument!";
    }
}
