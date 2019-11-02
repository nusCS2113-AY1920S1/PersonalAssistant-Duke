package wallet.exception;

import java.time.DateTimeException;

public class WrongDateTimeFormat extends DateTimeException {
    private String message;

    public WrongDateTimeFormat(String errorMessage) {
        super(errorMessage);
        this.message = errorMessage;
    }

    public String toString() {
        return this.message;
    }
}
