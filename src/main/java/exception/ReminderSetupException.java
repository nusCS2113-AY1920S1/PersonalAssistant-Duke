package exception;

public class ReminderSetupException extends WordUpException {
    public ReminderSetupException() {
        super(" OOPS: Something went wrong setting up the reminder!");
    }
}
