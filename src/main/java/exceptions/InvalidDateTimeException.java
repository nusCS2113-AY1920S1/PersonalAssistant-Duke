package exceptions;

public class InvalidDateTimeException extends DukeException {

    public InvalidDateTimeException() {
        super("Please ensure that date and time are in the format dd/MMMM/yyyy HHmm");
    }
}
