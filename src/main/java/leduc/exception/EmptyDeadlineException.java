package leduc.exception;

import leduc.Ui;
import leduc.exception.DukeException;

/**
 * Represent a exception when the description of the deadline task is not given by the user.
 */
public class EmptyDeadlineException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyDeadlineException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public EmptyDeadlineException(Ui ui){
        super(ui);
    }

    /**
     * Ask for a description for the deadline task to the user.
     */
    public void print(){
        super.ui.display("\t emptyDeadlineException:\n\t\t ☹ OOPS!!! The description of a deadline task cannot be empty");
    }
}
