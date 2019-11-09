package spinbox.exceptions;

public class ScheduleDateException extends SpinBoxException {
    public ScheduleDateException(String errorMsg) {
        super("Event Date Exception\n" + errorMsg);
    }
}
