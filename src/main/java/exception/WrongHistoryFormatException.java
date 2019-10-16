package exception;

public class WrongHistoryFormatException extends WrongFormatException {
    public WrongHistoryFormatException() {
        super(" OOPS: Expected format \"history {int value}\"");
    }
}
