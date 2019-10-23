package planner.exceptions.original;

public class ModBadGradeException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Please enter a valid letter grade!";
    }
}
