package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

/**
 * Detects delete anomalies in user input.
 */
public class DeleteAnomaly extends DetectAnomaly {

    /**
     * Detect anomalies for input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format.
     */
    @Override
    public void detect(String[] userInput) throws CommandLineException {
        String[] splitInput = userInput[1].split(" ");

        int index;
        try {
            index = Integer.parseInt(splitInput[0]);
        } catch (NumberFormatException e) {
            throw new CommandLineException("Please enter a number after the command.");
        }

        index--;
        if (index >= Hustler.list.size()) {
            throw new CommandLineException("The task index provided is invalid.");
        }
    }
}
