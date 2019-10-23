package planner.exceptions.original;

public class ModScheduleException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "This task clashes with existing tasks!";
    }
}
