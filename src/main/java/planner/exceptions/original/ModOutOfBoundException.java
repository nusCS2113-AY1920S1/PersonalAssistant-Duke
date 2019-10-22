package planner.exceptions.original;

public class ModOutOfBoundException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Index out of bound, try something else!";
    }
}
