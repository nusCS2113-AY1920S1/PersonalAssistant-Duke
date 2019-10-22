package leduc.exception;

/**
 * Represent a exception when the description of the event task is not given by the user.
 */
public class EmptyEventException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyEventException.
     */
    public EmptyEventException(){
        super();
    }

    /**
     * Ask for a description for the event task to the user.
     * @return the error message
     */
    public String print(){
        return "\t emptyEventException:\n\t\t ☹ OOPS!!! The description of a event task cannot be empty";
    }
}
