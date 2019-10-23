package planner.exceptions.original;

public class ModBadSUException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "S/U option is not allowed for this module!";
    }
}
