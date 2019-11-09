package leduc.exception;

/**
 * Exception thrown when the format of the recurrence is not respected
 */
public class RecurrenceException extends DukeException {
    /**
     * Constructor of RecurrenceException
     */
    public RecurrenceException() {
        super();
    }

    /**
     * Tell the user to respect the format of recurrence
     * @return the error message
     */
    @Override
    public String print() {
        return "\t RecurrenceException:\n\t\t ☹ OOPS!!! Please respect the recurrence format" +
                "\n\t\t\t recu TYPEOFRECURRENCE NBRECURRENCE";
    }
}
