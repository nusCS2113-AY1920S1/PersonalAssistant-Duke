package duke.exceptions;

public class ModBadGradeException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "S/U option is not allowed for this module!";
    }
}
