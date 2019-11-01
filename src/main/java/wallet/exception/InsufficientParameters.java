package wallet.exception;

public class InsufficientParameters extends ArrayIndexOutOfBoundsException {

    private String message;

    public InsufficientParameters(String errorMessage) {
        super(errorMessage);
        this.message = errorMessage;
    }

    public String toString() {
        return this.message;
    }
}
