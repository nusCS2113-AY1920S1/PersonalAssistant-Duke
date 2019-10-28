package exception;

public class WrongReminderFormatException extends WordUpException {
    public WrongReminderFormatException() {
        super(" OOPS: Expected format: schedule");
    }
}
