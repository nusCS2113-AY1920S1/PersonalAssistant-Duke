package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

/**
 * Detects anomalies in find command inputted by user.
 */
public class FindAnomaly extends DetectAnomaly {

    /**
     * The message to output if the user gives the wrong format.
     */
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Find format should be: \"/find <keyword>\"\n\t"
        + "Keyword also cannot be empty!";

    /**
     * The message to output if the user attempts to query more than one keyword.
     */
    private static final String MESSAGE_ONE_KEYWORD_ONLY = "Please only find one keyword! Format should be \"find <keyword>\"";

    /**
     * Detects anomalies in find command input.
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length == 1 || userInput[1].isBlank()) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        } else if (userInput[1].split(" ").length != 1) {
            throw new CommandLineException(MESSAGE_ONE_KEYWORD_ONLY);
        }
    }
}
