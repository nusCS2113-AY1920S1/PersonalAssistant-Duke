/**
 * Represent a exception when the period of the event task is not given by the user.
 */
public class EmptyEventDateException extends DukeException {
    /**
     * Constructor of EmptyEventDateException.
     * @param ui Ui which deals with the interactions with the user.
     */
    public EmptyEventDateException(Ui ui){
        super(ui);
    }

    /**
     * Ask for a period for the event task to the user.
     */
    public void print(){
        super.ui.display("\t emptyEventDateException:\n\t\t ☹ OOPS!!! Please enter a period for the event task");
    }
}
