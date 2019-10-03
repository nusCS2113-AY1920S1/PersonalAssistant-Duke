package seedu.duke.logic;

import seedu.duke.ui.Ui;

/**
 * Helps print out errors to prompt the user of the proper input.
 */
public class CommandLineException extends Exception {

    public CommandLineException(String message) {
        super(message);
    }

    /**
     * Prints out the error message in the command prompt.
     */
    public void getErrorMsg() {
        System.out.println(Ui.LINE);
        System.out.println(this.getMessage());
        System.out.println(Ui.LINE);
    }

}
