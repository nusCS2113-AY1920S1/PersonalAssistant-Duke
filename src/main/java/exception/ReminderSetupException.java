package exception;

public class ReminderSetupException extends WordUpException {
    public  ReminderSetupException() {
        super("OOPS: Something went wrong setting up the reminder!");
    }

    public ReminderSetupException(String reminderErrorMsg) {
        super(reminderErrorMsg + "Reschedule with 'schedule' or enter a new command.");
    }
}
