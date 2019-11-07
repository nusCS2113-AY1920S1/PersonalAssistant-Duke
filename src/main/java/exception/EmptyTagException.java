package exception;

public class EmptyTagException extends WordUpException {
    public EmptyTagException() {
        super(" OOPS: One of your input tags is empty");
    }
}
