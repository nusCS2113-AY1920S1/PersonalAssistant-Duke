package duke.data;

public class Result extends Evidence {

    /**
     * Represents results of an investigation based on the treatment prescribed for a patient.
     * A Result object corresponds to the result of an investigation into the symptoms of a Patient,
     * the particular impression, as well as an integer between 1-4 representing the priority
     * or significance of the result.
     * Attributes:
     * @param name the result
     * @param impression the impression object the result is tagged to
     * @param summary a summary of the result
     * @param priority the priority level of the evidence
    */
    public Result(String name, Impression impression, int priority, String summary) {
        super(name, impression, priority, summary);
    }

    /**
     * Represents results of an investigation based on the treatment prescribed for a patient.
     * A Result object corresponds to the result of an investigation into the symptoms of a Patient,
     * the particular impression, as well as an integer between 1-4 representing the priority
     * or significance of the result.
     * Attributes:
     * @param name the result
     * @param impression the name of the impression object the result is tagged to
     * @param summary a summary of the result
     * @param priority the priority level of the evidence
     */
    protected Result(String name, String impression, int priority, String summary) {
        super(name, impression, priority, summary);
    }

    @Override
    public String toString() {
        // todo
        return null;
    }

    @Override
    public String toDisplayString() {
        // todo
        return null;
    }

    @Override
    public String toReportString() {
        // todo
        return null;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
