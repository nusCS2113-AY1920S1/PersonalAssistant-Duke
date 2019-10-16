package exception;

public class WrongListFormatDescription extends WrongFormatException {
    public WrongListFormatDescription() {
        super(" OOPS: Expected format \"list [o/ORDER]\"\n" +
                "(ORDER can be \"asc\" for ascending and \"desc\" for descending\n");
    }
}
