package leduc.exception;
/**
 * Thrown when an incorrect flag is entered by the user
 */
public class InvalidFlagException extends DukeException{

    /**
     * Constructor of leduc.exception.InvalidFlagException
     *
     */
    public InvalidFlagException(){
        super();
    }

    /**
     * Tell the user that the shortcut name already exist
     * @return the error message
     */
    public String print(){
        return "\t InvalidFlagException:\n\t\t ☹ Invalid Flag!";
    }
}
