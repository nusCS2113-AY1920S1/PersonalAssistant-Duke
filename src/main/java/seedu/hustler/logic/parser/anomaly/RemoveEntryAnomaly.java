package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.schedule.RecommendedSchedule;
import seedu.hustler.logic.CommandLineException;

public class RemoveEntryAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in user input.
     *
     * @param userInput the index issued by the user
     * @throws CommandLineException for anomalies detected
     */
    public void detect(String[] userInput) throws CommandLineException {
        int index = -1;
        try {
            index = Integer.parseInt(userInput[1]);
            index--;
            RecommendedSchedule.recommended.get(index);
        } catch (NumberFormatException e) {
            throw new CommandLineException("Please issue an index after the command: /command <index>");
        } catch (IndexOutOfBoundsException e) {
            throw new CommandLineException("Please enter the correct index.");
        }
    } 
}
