package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

/**
 * Detects find anomalies in user input.
 */
public class FindAnomaly extends DetectAnomaly {

    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput[1].isBlank()) {
            throw new CommandLineException("Find format should be: '/find <keyword> ! Description of keyword cannot be empty!");
        }
    }
}
