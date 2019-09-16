package duke.exception;

/**
 * Exceptions which the user may reset his data in response to.
 */
public class DukeResetException extends DukeException {

    /**
     * Generates a DukeResetException with a message asking the user if he wants to reset his data. The catch handler
     * should have access to the Ui and Storage objects, ask the user if he wants to reset his data, and reset it if so.
     */
    public DukeResetException(String msg) {
        super(msg + " You can back up the file, and/or reset it!" + System.lineSeparator()
                + "Do you want to reset your Duke data now," + " to continue using Duke? (y/n)");
    }
}
