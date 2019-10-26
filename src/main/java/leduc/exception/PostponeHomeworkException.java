package leduc.exception;


/**
 * Represent a exception when the new homework deadline is before the old homework deadline.
 */
public class PostponeHomeworkException extends DukeException {
    /**
     * Constructor of leduc.exception.PostponeHomeworkException
     *
     */
    public PostponeHomeworkException(){
        super();
    }

    /**
     * Tell the user that the new homework's deadline should not be before the old one.
     * @return the error message
     */
    public String print(){
        return "\t PostponeHomeworkException:\n\t\t ☹ OOPS!!! The new homework should not be before the old one";
    }
}
