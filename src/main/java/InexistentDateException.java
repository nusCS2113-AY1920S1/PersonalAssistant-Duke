/**
 * Represent a exception when the date given does not exist.
 */
public class InexistentDateException extends DukeException {
    /**
     * Constructor of InexistentDateException.
     * @param ui Ui which deals with the interactions with the user.
     */
    public InexistentDateException(Ui ui){
        super(ui);
    }
    /**
     * Tell the user that the date given does not exist.
     */
    public void print(){
        super.ui.display("\t inexistentDateException:\n\t\t ☹ OOPS!!! \n\t\t\t The date doesn't exist" );
    }
}