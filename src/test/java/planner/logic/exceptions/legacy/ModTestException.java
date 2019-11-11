package planner.logic.exceptions.legacy;

public class ModTestException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "This is a test Exception!";
    }

}
