package planner.logic.exceptions.legacy;

public class ModOutOfBoundException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Index out of bound, try something else!";
    }
}
