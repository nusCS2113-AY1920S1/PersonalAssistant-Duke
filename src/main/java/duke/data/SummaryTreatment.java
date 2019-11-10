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

    /**
     * Edit this Treatment object. If a parameter is non-null, the corresponding member in the Treatment will be
     * replaced by the parameter's value.
     * @param newName The new name to assign to the treatment.
     * @param newPriority The new priority value to assign to the treatment.
     * @param editVals A map mapping member names to new values. If a value for "summary" is present, the treatment's
     *                 summary will be updated to that value.
     * @param isAppending If true, append all new data to the existing members. If false, overwrite the existing data.
     * @throws DukeException If a invalid value is supplied for any field.
     */
    public void edit(String newName, int newPriority, Map<String, String> editVals,
                     boolean isAppending)
            throws DukeException {
        super.edit(newName, newPriority, editVals, isAppending);
        String newSummary = editVals.get("summary");
        if (newSummary != null) {
            setSummary((isAppending) ? getSummary() + "\n\n" + newSummary : newSummary);
        }
    }

    @Override
    public boolean contains(String searchTerm) {
        return super.contains(searchTerm) || summary.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
