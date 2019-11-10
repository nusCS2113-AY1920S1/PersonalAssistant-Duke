package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

/**
 * Detects anomalies in add command inputted by user.
 */
public class EditDifficultyAnomaly {

    /**
     * Detects anomaly in edit description anomaly input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    public void detect(int index, String difficulty) throws CommandLineException {
        try {
            Hustler.list.get(index);
            if (!(difficulty.equals("H") || difficulty.equals("M") || difficulty.equals("L"))) {
                throw new CommandLineException("Difficulty can only be H, M, L"); 
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandLineException("Please enter a valid index.");
        }
    } 
}
