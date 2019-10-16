package duke.data;

public class Plan extends Treatment {

    private String summary;

    /**
     * Represents the Plan devised by the doctor to treat a symptom or impression of a patient.
     * A Plan object corresponds to the plan devised by a doctor to treat the signs and symptoms of a Patient,
     * the impression that led to that particular plan being necessary, the status of the treatment,
     * a description of the status, the summary of the plan as well as an integer between 1-4
     * representing the priority or significance of the plan.
     * Attributes:
     * @param name the generic plan name
     * @param impression the impression object the plan is tagged to
     * @param priority the priority level of the plan
     * @param status the current status of the plan
     * @param statusArr description of the status tagged to this plan
     * @param summary the summary of what the plan entails
     */
    public Plan(String name, Impression impression, int priority, int status, String[] statusArr, String summary) {
        super(name, impression, priority, status, statusArr);
        this.summary = summary;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String toDisplayString() {
        return null;
    }

    @Override
    public String toReportString() {
        return null;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
