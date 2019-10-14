package spinbox.exceptions;

public class InputException extends SpinBoxException {
    public InputException(String errorMsg) {
        super("Invalid Input\n\n" + errorMsg);
    }
}
