package spinbox.exceptions;

public class CalendarSelectorException extends SpinBoxException {
    private static final String ERROR_MESSAGE = "Error: Please supply 1, 2, 3 to corresponding"
            + " to day, month or year for calendar selection";

    public CalendarSelectorException() {
        super(ERROR_MESSAGE);
    }
}
