package exception;

public class WrongListTagFormatException extends WrongFormatException {
    public WrongListTagFormatException() {
        super(" OOPS: Expected format: \"list_tags\"");
    }
}