/**
 * A custom exception for Duke, when inputs don't fit the desired format or
 * have no meaning.
 */

public class DukeException extends Exception {
    protected String oops = "☹ OOPS!!! ";
    protected String errorMsg;

    /**
     * Constructor for a DukeException. Every DukeException contains an error message.
     * @param errorMsg the error message of this DukeException.
     */
    public DukeException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * Prints the error message in the standard Duke format.
     */
    public void showError() {
        System.out.println("\t____________________________________________________________");
        System.out.println("\t" + oops + errorMsg);
        System.out.println("\t____________________________________________________________");
    }
}