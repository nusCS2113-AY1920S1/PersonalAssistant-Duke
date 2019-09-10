/**
 * Represent a exception when the description of the event task is not given by the user.
 */
public class EmptyEventException extends DukeException {
    /**
     * Constructor of EmptyEventException.
     * @param ui Ui which deals with the interactions with the user.
     */
    public EmptyEventException(Ui ui){
        super(ui);
    }

    /**
     * Ask for a description for the event task to the user.
     */
    public void print(){
        super.ui.display("\t emptyEventException:\n\t\t ☹ OOPS!!! The description of a event task cannot be empty");
    }
}
