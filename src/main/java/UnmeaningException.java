/**
 * Represent a exception when input string of the user is not understood.
 */
public class UnmeaningException extends DukeException {
    /**
     * Constructor of UnmeaningException.
     * @param ui Ui which deals with the interactions with the user.
     */
    public UnmeaningException(Ui ui){
        super(ui);
    }

    /**
     * Tell the user that the input String is not understood.
     */
    public void print(){
        super.ui.display("\t unmeaningException:\n\t\t OOPS!!! I'm sorry, but I don't know what that means :-(\"");
    }
}
