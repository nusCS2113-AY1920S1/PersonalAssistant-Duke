package seedu.hustler.logic;

import seedu.hustler.ui.Ui;

/**
 * Helps print out errors to prompt the user of the proper input.
 */
public class CommandLineException extends Exception {

    public CommandLineException(String message) {
        super(message);
    }
}