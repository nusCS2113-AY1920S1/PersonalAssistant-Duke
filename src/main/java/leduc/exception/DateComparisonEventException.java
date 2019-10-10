package leduc.exception;


/**
 * Represent a exception when the second date is before the first one.
 */
public class DateComparisonEventException extends DukeException {
    /**
     * Constructor of leduc.exception.DateComparisonEventException
     *
     */
    public DateComparisonEventException(){
        super();
    }

    /**
     * Tell the user that the second date should not be before the first one.
     * @return the error message
     */
    public String print(){
        return "\t DateComparisonEventException:\n\t\t ☹ OOPS!!! The second date should not be before the first one.";
    }
}
