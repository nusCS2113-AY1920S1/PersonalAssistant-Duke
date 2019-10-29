package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

public class SetNameAnomaly extends DetectAnomaly {

    private final String BLANK_NAME_MESSAGE = "Name cannot be empty! Please input \"/setname <name>\".";

    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if(userInput.length == 1) {
            throw new CommandLineException(BLANK_NAME_MESSAGE);
        }
    }
}
