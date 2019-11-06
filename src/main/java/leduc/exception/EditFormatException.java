package leduc.exception;


/**
 * Represent a exception when the format of a one shot edit command is not respected.
 */
public class EditFormatException extends DukeException {
    /**
     * Constructor of leduc.exception.EditFormatException.
     */
    public EditFormatException(){
        super();
    }

    /**
     * Provide the one shot edit format to respect to the user.
     *
     */
    public String print(){
        return "\t EditFormatException:\n\t\t ☹ OOPS!!! Please respect the edit command format" +
                "\n\t\t For multi-step command : 'edit' and then follow the instructions" +
                "\n\t\t For one shot command: " +
                "\n\t\t\t edit the description: 'edit INDEX description DESCRIPTION' " +
                "\n\t\t\t edit the date of an homework task: 'edit INDEX /by DATE' " +
                "\n\t\t\t edit the period of an event task: 'edit INDEX /at DATE - DATE'";
    }
}
