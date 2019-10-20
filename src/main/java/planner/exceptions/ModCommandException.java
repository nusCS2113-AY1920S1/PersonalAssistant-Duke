package planner.exceptions;

public class ModCommandException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Must be a valid command!";
    }
}
