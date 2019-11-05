package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

import java.util.Arrays;

/**
 * Detects anomalies in sort command inputted by user.
 */
public class SortAnomaly extends DetectAnomaly {

    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sort format should be: /sort <sortType>";
    private static final String MESSAGE_EMPTY_SORT_TYPE = "The description of <sortType> cannot be empty!";
    private static final String MESSAGE_INVALID_SORT_TYPE = "The <sortType> provided is invalid!\n\t"
        + "Valid <sortType> are normal/datetime/priority.";

    /**
     * Detects anomalies in sort command input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length == 1 || userInput[1].isBlank()) {
            throw new CommandLineException(MESSAGE_EMPTY_SORT_TYPE);
        }

        String[] parsedInput = userInput[1].split(" ");
        if (parsedInput.length != 1) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String sortType = userInput[1];
        String[] validSortTypes = {"normal", "datetime", "priority"};
        if (!Arrays.asList(validSortTypes).contains(sortType.toLowerCase())) {
            throw new CommandLineException(MESSAGE_INVALID_SORT_TYPE);
        }
    }
}
