package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

/**
 * Detects anomalies in add command inputted by user.
 */
public class EditDescriptionAnomaly {

    /**
     * Detects anomaly in edit description anomaly input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    public void detect(int index, String description) throws CommandLineException {
        if (description.isBlank()) {
            throw new CommandLineException("Tasks cannot have an empty description.");
        }

        try {
            Hustler.list.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandLineException("Please enter a valid index.");
        }
    } 
}
