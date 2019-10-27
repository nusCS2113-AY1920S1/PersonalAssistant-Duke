package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

public class OneWordAnomaly extends DetectAnomaly{

    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if(userInput.length >  1) {
            throw new CommandLineException("Please input a proper command!");
        }
    }
}
