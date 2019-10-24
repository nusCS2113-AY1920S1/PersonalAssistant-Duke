package duke.logic.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Enables the user to iterate through previous inputs one by one.
 * Pressing UP key once shows one input earlier.
 * Pressing more times shows much earlier inputs until the earliest input is reached.
 * Pressing DOWN key once traverses back to recent input.
 * Pressing more times shows more recent inputs until the most recent input is reached.
 * While the most recent input displayed, pressing DOWN Key will clear the textField.
 */
public class InputHistory {

    private static final int INITIAL_INDEX = 0;
    private static final String EMPTY_STRING = "";

    private List<String> inputHistory;
    private int iteratingIndex;

    /**
     * Creates an InputHistory to record user commands sent in textField of mainWindow.
     */
    public InputHistory() {
        inputHistory = new ArrayList<String>();
        iteratingIndex = INITIAL_INDEX;
    }

    /**
     * Adds the input command from textField into InputHistory after it is executed.
     *
     * @param newInput The input command to be recorded.
     */
    public void add(String newInput) {
        inputHistory.add(newInput);
        iteratingIndex = inputHistory.size();
    }

    /**
     * Tests whether there are still available earlier commands.
     *
     * @return True if earlier commands can be found in the record and false otherwise.
     */
    public boolean isAbleToLast() {
        return iteratingIndex >= 1;
    }

    /**
     * Tests whether there are still available later commands.
     *
     * @return True if later commands can be found in the record and false otherwise.
     */
    public boolean isAbleToNext() {
        return iteratingIndex < inputHistory.size();
    }

    /**
     * Gets the one earlier command.
     *
     * @return The earlier command as {@code String}
     */
    public String getLastInput() {
        if(isAbleToLast()) {
            iteratingIndex = iteratingIndex - 1;
        }
        return inputHistory.get(iteratingIndex);
    }

    /**
     * Gets the one later command.
     *
     * @return The later command as {@code String}
     */
    public String getNextInput() {
        if(isAbleToNext()) {
            iteratingIndex = iteratingIndex + 1;
        }
        if(iteratingIndex == inputHistory.size()) {
           return EMPTY_STRING;
        } else {
            return inputHistory.get(iteratingIndex);
        }
    }
}
