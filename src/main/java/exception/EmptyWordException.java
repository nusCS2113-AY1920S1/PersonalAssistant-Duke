package exception;

public class EmptyWordException extends DukeException {
    public EmptyWordException() {
        super("☹ OOPS: Your input word is empty");
    }
}
