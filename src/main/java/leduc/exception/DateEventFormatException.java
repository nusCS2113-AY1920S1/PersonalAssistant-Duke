package leduc.exception;

import leduc.Ui;

/**
 * Represent a exception when the date format of a event task is not respected.
 */
public class DateEventFormatException extends DukeException {
    /**
     * Constructor of leduc.exception.DateEventFormatException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public DateEventFormatException(Ui ui){
        super(ui);
    }

    /**
     * Provide the date format of a event task to respect to the user.
     */
    public void print(){
        super.ui.display("\t dateEventFormatException:\n\t\t ☹ OOPS!!! Please respect the date format for an event" +
                "\n\t\t\t dd/MM/yyyy HH:mm - dd/MM/yyyy HH:mm");
    }
}
