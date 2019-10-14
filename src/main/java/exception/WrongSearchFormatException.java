package exception;

public class WrongSearchFormatException extends WrongFormatException {
    public WrongSearchFormatException() {
        super("☹ OOPS: Expected format \"search w/WORD_TO_BE_SEARCHED\"");
    }
}
