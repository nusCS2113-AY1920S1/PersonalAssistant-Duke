package duke.exceptions;

public class DukeMultipleValuesForSameArgumentException extends DukeException {
    @Override
    public String getMessage() {
        return super.getMessage() + "Cannot set multiple values for same argument!";
    }
}
