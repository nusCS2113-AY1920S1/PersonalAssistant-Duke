package exception;

public class EmptyWordException extends WordUpException {
    public EmptyWordException() {
        super("☹ OOPS: Your input word is empty");
    }
}
