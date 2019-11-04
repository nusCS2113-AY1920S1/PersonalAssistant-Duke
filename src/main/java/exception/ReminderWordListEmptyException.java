package exception;

public class ReminderWordListEmptyException extends ReminderSetupException {
    public ReminderWordListEmptyException() {
        super(" OOPS: Please enter at least one word.\n");
    }
}