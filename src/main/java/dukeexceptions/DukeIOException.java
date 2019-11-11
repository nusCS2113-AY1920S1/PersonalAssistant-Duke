package dukeexceptions;

import java.io.IOException;

/**
 * Represents the exception specifically catered to
 * IO exception occurring in Duke.
 */
public class DukeIOException extends IOException {
    public DukeIOException(String message) {
        super(message);
    }

}
