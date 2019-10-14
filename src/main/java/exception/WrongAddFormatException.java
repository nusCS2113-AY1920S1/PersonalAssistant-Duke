package exception;

public class WrongAddFormatException extends WrongFormatException {
    public WrongAddFormatException() {
        super("     ☹ OOPS: Expected format \"add w/WORD w/MEANING [t/TAG]\"");
    }
}
