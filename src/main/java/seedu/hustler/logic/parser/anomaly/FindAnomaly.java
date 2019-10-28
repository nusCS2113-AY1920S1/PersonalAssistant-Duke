package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

/**
 * Detects anomalies in find command inputted by user.
 */
public class FindAnomaly extends DetectAnomaly {

    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Find format should be: /find <keyword>\n\t"
        + "Keyword also cannot be empty!";

    /**
     * Detects anomalies in find command input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length == 1) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
        if (userInput[1].isBlank()) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
}
