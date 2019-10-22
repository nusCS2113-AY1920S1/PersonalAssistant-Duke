package controlpanel;

/**
 * A self defined exception class.
 */
public class DukeException extends Exception {

    /**
     * A default constructor.
     */
    DukeException() {
    }

    /**
     * The constructor to throw the errors.
     *
     * @param errorMsg The error message sent from the faulty object.
     */
    //@@ cctt1014
    public DukeException(String errorMsg) {
        super(errorMsg);
    }
}