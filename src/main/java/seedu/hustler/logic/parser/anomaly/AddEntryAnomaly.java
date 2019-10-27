package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

public class AddEntryAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in user input.
     *
     * @param userInput the index issued by the user
     */
    public void detect(String[] userInput) throws CommandLineException {
        int index = -1;
        try {
            index = Integer.parseInt(userInput[1]);
            index--;
            Hustler.list.get(index);
        } catch (NumberFormatException e) {
            throw new CommandLineException("Please issue a number after the command: /command <index>");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandLineException("Please enter the current index of task.");
        }
    } 
}
