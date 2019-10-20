package planner.exceptions;

public class ModInvalidIndexException extends ModException {
    @Override
    public String getMessage() {
        return super.getMessage() + "Invalid Index!";
    }
}

