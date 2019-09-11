package leduc.exception;

import leduc.Ui;
import leduc.exception.DukeException;

/**
 * Represent a exception when the description of the todo task is not given by the user.
 */
public class EmptyTodoException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyTodoException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public EmptyTodoException(Ui ui){
        super(ui);
    }

    /**
     * Ask for a description for the todo task to the user.
     */
    public void print(){
        super.ui.display("\t emptyTodoException:\n\t\t ☹ OOPS!!! The description of a todo cannot be empty.");
    }
}
