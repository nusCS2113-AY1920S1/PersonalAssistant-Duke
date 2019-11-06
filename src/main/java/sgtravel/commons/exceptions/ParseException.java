package sgtravel.commons.exceptions;

public class ParseException extends DukeException {

    public ParseException(String message) {
        super(message + "\nRefer to help for format of instructions.");
    }
}
