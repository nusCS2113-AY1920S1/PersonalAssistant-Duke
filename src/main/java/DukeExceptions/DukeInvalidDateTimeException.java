package DukeExceptions;

import java.text.ParseException;

public class DukeInvalidDateTimeException extends Exception {
    public DukeInvalidDateTimeException(String message) {
        super(message);
    }
}
