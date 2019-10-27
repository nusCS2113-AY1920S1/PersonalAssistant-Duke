package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.ui.Ui;
import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

/**
 * Detects /done anomalies in user input.
 */
public class DoneAnomaly extends DetectAnomaly {

    /**
     * Detects done anomalies for input.
     *
     * @param userInput input for which anomaly is detected
     * @return true or false for any anomaly detected
     */
    public void detect(String[] userInput) throws CommandLineException {

        Ui ui = new Ui();

        //detects if the /done command is followed by any arguments.
        if (userInput.length == 1) {
            throw new CommandLineException("Done format should be: '/done <integer>'!");
        }

        String[] parsedInput = userInput[1].split(" ");

        //detects whether the number of arguments are exactly two. For
	//example, 'done 1 2 3' and 'done 6 7 8 9' are invalid inputs.
        if (parsedInput.length > 1) {
            throw new CommandLineException("Done format should be: '/done <integer>'!");
        }

        int doneIndex;
        //detects whether the index argument provided is a integer. For
	//example, 'done lor' is a invalid input.
        try {
            doneIndex = Integer.parseInt(parsedInput[0]);
        } catch (NumberFormatException e) {
            throw new CommandLineException("Done format should be: '/done <integer>'!");
        }

        doneIndex--;

        //detects array out of bounds exception.
        if (doneIndex >= Hustler.list.size()) {
            throw new CommandLineException("The task index provided is invalid.");
        }
    }
}
