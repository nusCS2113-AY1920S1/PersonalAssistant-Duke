package leduc.exception;

/**
 * Represent a exception when the deadline date of the homework task is not given by the user.
 */
public class EmptyHomeworkDateException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyDeadlineDateException.
     */
    public EmptyHomeworkDateException(){
        super();
    }

    /**
     * Ask for a deadline date for the homework task to the user.
     * @return the error message
     */
    public String print(){
        return "\t emptyHomeworkDateException:\n\t\t ☹ OOPS!!! Please enter a deadline for the task";
    }
}
