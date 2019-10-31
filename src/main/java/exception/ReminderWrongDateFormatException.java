package exception;

public class ReminderWrongDateFormatException extends ReminderSetupException {
    public ReminderWrongDateFormatException() {
        super(" OOPS: Expected date format: \"dd-MM-yyyy HHmm\n");
    }
}