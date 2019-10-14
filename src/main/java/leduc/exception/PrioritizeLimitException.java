package leduc.exception;


/**
 * Represent a exception when the new priority is greater than 9 or less than 0.
 */
public class PrioritizeLimitException extends DukeException {
    /**
     * Constructor of leduc.exception.PrioritizeLimitException.
     */
    public PrioritizeLimitException(){
        super();
    }

    /**
     * Tells the users that priority can’t be less than 0 and more than 9.
     *
     */
    public String print(){
        return "\t PrioritizeLimitException:\n\t\t ☹ OOPS!!! The priority of a task can’t be less than 0 or more than 9.";
    }
}
