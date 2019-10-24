package planner.exceptions.original;

public class ModBadSuException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "S/U option is not allowed for this module!";
    }
}
