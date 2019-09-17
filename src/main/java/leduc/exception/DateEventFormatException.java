package leduc.exception;

import leduc.Ui;

/**
 * Represent a exception when the date format of a event task is not respected.
 */
public class DateEventFormatException extends DukeException {
    /**
     * Constructor of leduc.exception.DateEventFormatException.
     */
    public DateEventFormatException(){
        super();
    }

    /**
     * Provide the date format of a event task to respect to the user.
     *
     */
    public String print(){
        return "\t dateEventFormatException:\n\t\t ☹ OOPS!!! Please respect the date format for an event" +
                "\n\t\t\t dd/MM/yyyy HH:mm - dd/MM/yyyy HH:mm";
    }
}
