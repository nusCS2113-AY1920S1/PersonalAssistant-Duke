package leduc.exception;

/**
 * Represent a exception when the task to mark done or to delete does not exist.
 */
public class NonExistentTaskException extends DukeException {
    /**
     * Constructor of leduc.exception.NonExistentTaskException.
     *
     */
    public NonExistentTaskException(){
        super();
    }

    /**
     * Tell the user that the tasks given does not exist.
     * @return the error message
     */
    public String print(){
        return "\t NonExistentTaskException:\n\t\t ☹ OOPS!!! The task doesn't exist";
    }
}
