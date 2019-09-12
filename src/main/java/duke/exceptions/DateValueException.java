package duke.exceptions;

public class DateValueException extends DukeException {
    public DateValueException(String errorMsg) {
        super("Invalid Date Values\n" + errorMsg);
    }
}
