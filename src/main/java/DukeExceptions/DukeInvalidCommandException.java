package DukeExceptions;

/**
 * Represents the exception specifically catered to
 * invalid command input by user.
 */
public class DukeInvalidCommandException extends DukeException{
    public DukeInvalidCommandException(String message) {
        super(message);
    }

}
