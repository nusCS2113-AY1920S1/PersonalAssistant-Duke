package leduc.exception;


/**
 * Represent a exception when the new priority is not an int or is greater than 9 or less than 0.
 */
public class PrioritizeLimitException extends DukeException {
    /**
     * Constructor of leduc.exception.PrioritizeLimitException.
     */
    public PrioritizeLimitException(){
        super();
    }

    /**
     * Tells the users that priority should be an int greater than or equal to 0 and less than or equal than 9.
     *
     */
    public String print(){
        return "\t PrioritizeLimitException:\n\t\t ☹ OOPS!!! The priority of a task should be an int greater than or equal to  0 and less than or equal to 9.";
    }
}
