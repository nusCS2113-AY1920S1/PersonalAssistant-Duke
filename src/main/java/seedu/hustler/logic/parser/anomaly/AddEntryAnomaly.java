package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.ui.Ui;
import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

public class AddEntryAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in user input.
     *
     * @param userInput the index issued by the user
     * @return true or false for anomaly detected
     */
    public boolean detect(String[] userInput) throws CommandLineException {
        Ui ui = new Ui();
        int index = -1;
        try {
            index = Integer.parseInt(userInput[1]);
        } catch (NumberFormatException e) {
            throw CommandLineException("Please issue a number after the command: /command <index>");
            return true;
        }
        index--;
        try {
            Hustler.list.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw CommandLineException("Please enter the current index of task.");
            return true;
        }
        return false;
    } 
}
