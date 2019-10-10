package leduc.exception;


/**
 * Represent a exception when the new deadline is before the old deadline.
 */
public class PostponeDeadlineException extends DukeException {
    /**
     * Constructor of leduc.exception.PostponeDeadlineException
     *
     */
    public PostponeDeadlineException(){
        super();
    }

    /**
     * Tell the user that the new deadline should not be before the old one.
     * @return the error message
     */
    public String print(){
        return "\t PostponeDeadlineException:\n\t\t ☹ OOPS!!! The new deadline should not be before the old one";
    }
}
