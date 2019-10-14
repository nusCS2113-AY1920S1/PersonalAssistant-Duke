package spinbox.exceptions;

public class DateFormatException extends SpinBoxException {
    public DateFormatException(String errorMsg) {
        super("Invalid Date Format\n\n" + errorMsg);
    }
}
