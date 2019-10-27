package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.ui.Ui;
import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

/**
 * Detects undo anomalies in user input.
 */
public class UndoAnomaly extends DetectAnomaly {

    /**
     * Detects anomalies for the undo command..
     *
     * @param userInput input for which anomaly is detected
     * @return true or false for any anomaly detected
     */
    public boolean detect(String[] userInput) throws CommandLineException {

        Ui ui = new Ui();
        String[] parsedInput = userInput[1].split(" ");

        //detects whether they are more arguments than expected. For example,
	//'undo 1 2 3' and 'undo 3 fried rice' are invalid inputs.
        if (parsedInput.length != 1) {
            throw new CommandLineException("Undo commands should follow this format: 'undo <integer>'!");
	}

	    //detects whether the argument is a non-integer. For example, 'undo
	    //tacobell' is a invalid input.
        try {
            int numberOfCommandsToUndo = Integer.parseInt(parsedInput[0]);
        } catch (NumberFormatException e) {
            throw new CommandLineException("Undo commands should follow this format: 'undo <integer>'!");
        }

        return false;
    }
}
