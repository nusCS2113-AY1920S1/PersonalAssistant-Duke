package leduc.exception;

/**
 * Represent a exception when input string of the user is not understood.
 */
public class MeaninglessException extends DukeException {
    /**
     * Constructor of leduc.exception.MeaningLessException.
     */
    public MeaninglessException(){
        super();
    }

    /**
     * Tell the user that the input String is not known.
     * @return the error message
     */
    public String print(){
        return "\t MeaninglessException:\n\t\t OOPS!!! I'm sorry, but I don't know what that means :-(\"";
    }
}
