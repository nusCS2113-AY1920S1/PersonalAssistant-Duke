package exception;

public class InvalidHistoryIndexException extends WordUpException {
    public InvalidHistoryIndexException() {
        super(" OOPS: Past entry values cannot be negative.\"");
    }
}
