package duke.exception;

import duke.command.*;
import duke.core.*;
import duke.task.*;

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
        System.out.println("\t_____________________________________");
        System.out.println("\t" + oops + errorMsg);
        System.out.println("\t____________________________________________________________");
    }
}