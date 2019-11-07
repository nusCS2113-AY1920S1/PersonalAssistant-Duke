package exception;

public class WrongListFormatException extends WrongFormatException {
    public WrongListFormatException() {
        super(" OOPS: Expected format \"list [o/ORDER]\"\n"
                + "(ORDER can be \"asc\" for ascending and \"desc\" for descending\n");
    }
}
