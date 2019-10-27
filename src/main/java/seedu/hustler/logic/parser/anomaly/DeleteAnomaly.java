package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

/**
 * Detects anomalies in delete command inputted by user.
 */
public class DeleteAnomaly extends DetectAnomaly {

    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Delete format should be: '/delete <integer>' or '/delete all'";
    private static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid!";

    /**
     * Detects anomalies in delete command input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length == 1 || userInput[1].isBlank()) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String[] parsedInput = userInput[1].split(" ");
        if (parsedInput.length > 1) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        if (!isNumeric(parsedInput[0])) {
            if (!parsedInput[0].equals("all")) {
                throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            return;
        }

        int deleteIndex;
        try {
            deleteIndex = Integer.parseInt(parsedInput[0]);
        } catch (NumberFormatException e) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        deleteIndex--;
        if (deleteIndex >= Hustler.list.size() || deleteIndex < 0) {
            throw new CommandLineException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks if given string is numeric.
     *
     * @param string string to be checked.
     * @return true if string represents an integer, false otherwise
     */
    private boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
