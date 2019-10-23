package planner.exceptions.original;

public class ModCcaScheduleException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "This CCA clashes with existing CCAs!";
    }
}
