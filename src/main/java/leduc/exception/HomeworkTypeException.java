package leduc.exception;
/**
 * Represent a exception when the task is not a homework task while it should be
 */
public class HomeworkTypeException extends DukeException {
    /**
     * Constructor of leduc.exception.DeadlineTypeException
     *
     */
    public HomeworkTypeException(){
        super();
    }

    /**
     * Tell the user that the tasks chosen should be a homework task.
     * @return the error message
     */
    public String print(){
        return "\t HomeworkTypeException:\n\t\t ☹ OOPS!!! The task should be a homework task";
    }

}
