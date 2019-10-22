package planner.logic.exceptions.legacy;

public class ModCommandException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Must be a valid command!";
    }
}
