package leduc.exception;

/**
 * Represent a exception when the period of the event task is not given by the user.
 */
public class EmptyEventDateException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyEventDateException.
     */
    public EmptyEventDateException(){
        super();
    }

    /**
     * Ask for a period for the event task to the user.
     * @return the error message
     */
    public String print(){
        return "\t emptyEventDateException:\n\t\t ☹ OOPS!!! Please enter a period for the event task";
    }
}
