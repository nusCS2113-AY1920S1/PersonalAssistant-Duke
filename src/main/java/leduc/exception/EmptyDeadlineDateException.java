package leduc.exception;

/**
 * Represent a exception when the deadline date of the deadline task is not given by the user.
 */
public class EmptyDeadlineDateException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyDeadlineDateException.
     */
    public EmptyDeadlineDateException(){
        super();
    }

    /**
     * Ask for a deadline date for the deadline task to the user.
     * @return the error message
     */
    public String print(){
        return "\t emptyDeadlineDateException:\n\t\t ☹ OOPS!!! Please enter a deadline for the task";
    }
}
