package wallet.exception;

public class WrongParameterFormat extends NumberFormatException {

    private String message;

    public WrongParameterFormat(String errorMessage) {
        super(errorMessage);
        this.message = errorMessage;
    }

    public String toString() {
        return this.message;
    }
}
