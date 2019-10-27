package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

/**
 * Detects anomalies in sort command inputted by user.
 */
public class SortAnomaly extends DetectAnomaly {

    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sort format should be: /sort <sortType>";
    private static final String MESSAGE_INVALID_SORT_TYPE = "The sort type provided is invalid!\n\t"
        + "Valid sort types are: normal/datetime/priority.";

    /**
     * Detects anomalies in sort command input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length == 1) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String[] parsedInput = userInput[1].split(" ");
        if (parsedInput.length != 1) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String sortType = parsedInput[0];
        String[] sortTypes = {"normal", "datetime", "priority"};
        for (String validSortType: sortTypes) {
            if (sortType.equals(validSortType)) {
                return;
            }
        }
        throw new CommandLineException(MESSAGE_INVALID_SORT_TYPE);
    }
}
