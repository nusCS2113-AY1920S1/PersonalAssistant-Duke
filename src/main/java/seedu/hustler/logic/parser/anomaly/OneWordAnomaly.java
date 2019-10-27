package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

public class OneWordAnomaly extends DetectAnomaly {

    private final String ONE_WORD_MSG = "Please only input \"/<command>\"";

    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length != 1) {
            throw new CommandLineException(ONE_WORD_MSG);
        }
    }
}
