package exceptions;

public class InvalidInputException extends DukeException {

    public InvalidInputException(String error) {
        super("Sorry! I don't understand what you mean by: " + error);
    }
}
