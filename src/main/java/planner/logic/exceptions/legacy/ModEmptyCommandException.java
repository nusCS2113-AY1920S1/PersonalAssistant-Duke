package planner.logic.exceptions.legacy;

public class ModEmptyCommandException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Command cannot be empty!";
    }
}
