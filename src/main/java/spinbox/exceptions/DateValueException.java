package spinbox.exceptions;

public class DateValueException extends SpinBoxException {
    public DateValueException(String errorMsg) {
        super("Invalid Date Values\n" + errorMsg);
    }
}
