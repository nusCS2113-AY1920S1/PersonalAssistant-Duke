package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

public class OneWordAnomaly extends DetectAnomaly {

    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Please only input \"/<command>\"";

    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length != 1) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
}
