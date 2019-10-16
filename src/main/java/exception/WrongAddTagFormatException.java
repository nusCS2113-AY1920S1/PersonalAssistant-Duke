package exception;

public class WrongAddTagFormatException extends WrongFormatException {
    public WrongAddTagFormatException() {
        super(" OOPS: Expected format \"tag w/WORD t/TAG...\"\n");
    }
}
