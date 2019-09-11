package leduc.exception;

import leduc.Ui;

/**
 * Represent a exception when the date format of a deadline task is not respected.
 */
public class DateFormatException extends DukeException {
    /**
     * Constructor of leduc.exception.DateFormatException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public DateFormatException(Ui ui){
        super(ui);
    }

    /**
     * Provide the date format of a deadline task to respect to the user.
     */
    public void print(){
        super.ui.display("\t dateFormatException:\n\t\t ☹ OOPS!!! Please respect the date format\n\t\t\t " +
                "dd/MM/yyyy HH:mm");
    }
}
