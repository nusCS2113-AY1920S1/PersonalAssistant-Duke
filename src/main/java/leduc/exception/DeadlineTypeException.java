package leduc.exception;




/**
 * Represent a exception when the task is not a deadline task while it should be
 */
public class DeadlineTypeException extends DukeException {
    /**
     * Constructor of leduc.exception.DeadlineTypeException
     *
     */
    public DeadlineTypeException(){
        super();
    }

    /**
     * Tell the user that the tasks chosen should be a deadline task.
     * @return the error message
     */
    public String print(){
        return "\t DeadlineTypeException:\n\t\t ☹ OOPS!!! The task should be a deadline task";
    }

}
