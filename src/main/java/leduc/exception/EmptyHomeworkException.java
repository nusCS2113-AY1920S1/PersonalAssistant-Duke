package leduc.exception;

/**
 * Represent a exception when the description of the homework task is not given by the user.
 */
public class EmptyHomeworkException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyDeadlineException.
     */
    public EmptyHomeworkException(){
        super();
    }

    /**
     * Ask for a description for the homework task to the user.
     * @return the error message
     */
    public String print(){
        return "\t emptyHomeworkException:\n\t\t ☹ OOPS!!! The description of a homework task cannot be empty";
    }
}
