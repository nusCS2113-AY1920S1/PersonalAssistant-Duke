package duke.data;

import duke.exception.DukeException;

import java.util.Map;

public abstract class SummaryTreatment extends Treatment {

    protected String summary;

    // TODO update javadocs
    /**
     * Represents observations of a doctor about a patient.
     * An Observation object corresponds to the observations of the doctor about the symptoms of a Patient,
     * the information that led to that particular observation, as well as an integer between 1-4
     * representing the priority or significance of the evidence.
     * Attributes:
     * @param name information on the observation / symptom
     * @param impression the impression object the evidence is tagged to
     * @param summary a summary of what led to the observation
     * @param priority the priority level of the evidence
     */
    public SummaryTreatment(String name, Impression impression, int priority, String status, String summary)
            throws DukeException {
        super(name, impression, priority, status);
        this.summary = summary;
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
        informationString = "Summary: " + summary + "\n";
        return super.toString() + informationString;
    }

    @Override
    public String toReportString() {
        String informationString;
        informationString = "Summary: " + summary + "\n";
        return informationString;
    }

    public void edit(String newName, int newPriority, Map<String, String> editVals,
                     boolean isAppending)
            throws DukeException {
        super.edit(newName, newPriority, editVals, isAppending);
        String newSummary = editVals.get("summary");
        if (newSummary != null) {
            setSummary((isAppending) ? getSummary() + newSummary : newSummary);
        }
    }

    @Override
    public boolean contains(String searchTerm) {
        return super.contains(searchTerm) || summary.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
