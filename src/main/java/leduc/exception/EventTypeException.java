package leduc.exception;


/**
 * Represent a exception when the task is not a event task while it should be.
 */
public class EventTypeException extends DukeException{
    /**
     * Constructor of leduc.exception.EventTypeException.
     *
     */
    public EventTypeException(){
        super();
    }

    /**
     * Tell the user that the task chosen is not a event task while it should be.
     * @return the error message
     */
    public String print(){
        return "\t EventTypeException:\n\t\t ☹ OOPS!!! The task should be a event task";
    }

}
