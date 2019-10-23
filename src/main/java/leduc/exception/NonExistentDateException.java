package leduc.exception;


/**
 * Represent a exception when the date given does not exist.
 */
public class NonExistentDateException extends DukeException {
    /**
     * Constructor of leduc.exception.NonExistentDateException.
     */
    public NonExistentDateException(){
        super();
    }
    /**
     * Tell the user that the date given does not exist.
     * @return the error message
     */
    public String print(){
        return "\t NonExistentDateException:\n\t\t ☹ OOPS!!! \n\t\t\t The date doesn't exist";
    }
}