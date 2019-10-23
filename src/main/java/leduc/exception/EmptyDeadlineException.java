package leduc.exception;

/**
 * Represent a exception when the description of the deadline task is not given by the user.
 */
public class EmptyDeadlineException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyDeadlineException.
     */
    public EmptyDeadlineException(){
        super();
    }

    /**
     * Ask for a description for the deadline task to the user.
     * @return the error message
     */
    public String print(){
        return "\t emptyDeadlineException:\n\t\t ☹ OOPS!!! The description of a deadline task cannot be empty";
    }
}
