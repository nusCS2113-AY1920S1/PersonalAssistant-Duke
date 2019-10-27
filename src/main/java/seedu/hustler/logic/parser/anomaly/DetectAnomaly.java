package seedu.hustler.logic.parser.anomaly;

/**
 * Detects anomalies in user input for each
 * command.
 */
public abstract class DetectAnomaly {

    /**
     * Detects anomalies for input.
     *
     * @param o input for which anomaly is detected
     * @return true or false for any anomaly detected
     */
    public abstract boolean detect(Object o);
}
