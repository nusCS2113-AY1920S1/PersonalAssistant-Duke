package leduc.exception;

import leduc.Ui;
import leduc.exception.DukeException;

/**
 * Represent a exception when the date given does not exist.
 */
public class NonExistentDateException extends DukeException {
    /**
     * Constructor of leduc.exception.NonExistentDateException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public NonExistentDateException(Ui ui){
        super(ui);
    }
    /**
     * Tell the user that the date given does not exist.
     */
    public void print(){
        super.ui.display("\t NonExistentDateException:\n\t\t ☹ OOPS!!! \n\t\t\t The date doesn't exist" );
    }
}