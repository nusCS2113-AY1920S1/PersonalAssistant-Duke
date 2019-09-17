package leduc.exception;

import leduc.Ui;
import leduc.exception.DukeException;

/**
 * Represent a exception when input string of the user is not understood.
 */
public class MeaninglessException extends DukeException {
    /**
     * Constructor of leduc.exception.MeaningLessException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public MeaninglessException(Ui ui){
        super();
    }

    /**
     * Tell the user that the input String is not understood.
     */
    public String print(){
        return "\t MeaningLessException:\n\t\t OOPS!!! I'm sorry, but I don't know what that means :-(\"";
    }
}
