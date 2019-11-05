package leduc.exception;


/**
 * Represent a exception when the start date is after the end date of an event task.
 */
public class EventDateException extends DukeException{
    /**
     * Constructor of leduc.exception.EventDateException.
     *
     */
    public EventDateException(){
        super();
    }

    /**
     * Tell the user that the start date is after the end date of an event task.
     * @return the error message
     */
    public String print(){
        return "\t EventDateException:\n\t\t ☹ OOPS!!! The start date should be before the end date for an event task";
    }

}
