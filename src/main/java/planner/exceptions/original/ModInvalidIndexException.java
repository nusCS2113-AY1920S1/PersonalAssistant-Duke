package planner.exceptions.original;

public class ModInvalidIndexException extends ModException {
    @Override
    public String getMessage() {
        return super.getMessage() + "Invalid Index!";
    }
}

