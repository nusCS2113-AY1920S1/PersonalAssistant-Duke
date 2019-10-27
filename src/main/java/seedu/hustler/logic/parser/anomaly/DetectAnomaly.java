package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;

/**
 * Detects anomalies in user input for each
 * command.
 */
public abstract class DetectAnomaly {

    /**
     * Detects anomalies for input.
     *
     * @param userInput input for which anomaly is detected
     * @return true or false for any anomaly detected
     */
    public abstract boolean detect(String[] userInput) throws CommandLineException;
}
