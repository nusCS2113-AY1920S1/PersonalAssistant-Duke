package leduc.exception;

import leduc.Ui;
import leduc.exception.DukeException;

/**
 * Represent a exception when the description of the event task is not given by the user.
 */
public class EmptyEventException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyEventException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public EmptyEventException(Ui ui){
        super();
    }

    /**
     * Ask for a description for the event task to the user.
     */
    public String print(){
        return "\t emptyEventException:\n\t\t ☹ OOPS!!! The description of a event task cannot be empty";
    }
}
