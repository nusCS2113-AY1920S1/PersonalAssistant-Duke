package duke.data;

import duke.exception.DukeException;

/*
 * Abstraction of evidence constituting a medical diagnosis.
 * An Evidence object corresponds to the notes of the doctor
 * on a piece of medical evidence as well as
 * an integer between 1-4 representing the priority or significance of the evidence.
 *
 * Attributes:
 * - name: information on the evidence
 * - impression: the impression object the evidence is tagged to
 * - priority: the priority level of the evidence
 */
public abstract class Evidence extends DukeData {

    private String summary;

    public Evidence(String name, Impression impression, Integer priority, String summary) {
        super(name, impression, priority);
        this.summary = summary;
    }

    protected Evidence(String name, String impression, Integer priority, String summary) {
        super(name, impression, priority);
        this.summary = summary;
    }

    @Override
    public Integer updatePriority(Integer priorityVal) throws DukeException {
        if (priorityVal >= 0 && priorityVal < 5) {
            super.setPriority(priorityVal);
            return super.getPriority();
        } else {
            throw new DukeException("The priority must be within 0 to 4!");
        }
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Summary: " + this.summary + "\n";
        return super.toString() + informationString;
    }
}
