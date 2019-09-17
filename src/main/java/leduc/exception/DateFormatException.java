package leduc.exception;

import leduc.Ui;

/**
 * Represent a exception when the date format of a deadline task is not respected.
 */
public class DateFormatException extends DukeException {
    /**
     * Constructor of leduc.exception.DateFormatException.
     */
    public DateFormatException(){
        super();
    }

    /**
     * Provide the date format of a deadline task to respect to the user.
     * @return the error message
     */
    public String print(){
        return "\t dateFormatException:\n\t\t ☹ OOPS!!! Please respect the date format\n\t\t\t " +
                "dd/MM/yyyy HH:mm";
    }
}
