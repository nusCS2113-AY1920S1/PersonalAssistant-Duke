package exception;

public class WrongSearchFrequencyFormatException extends WrongFormatException {
    public WrongSearchFrequencyFormatException() {
        super(" OOPS: Expected format \"freq [o/ORDER]\"\n"
                + "(ORDER can be \"asc\" for ascending and \"desc\" for descending\n");
    }
}