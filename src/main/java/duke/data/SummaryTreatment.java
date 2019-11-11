package duke.data;

import duke.exception.DukeException;

import java.util.Map;

public abstract class SummaryTreatment extends Treatment {

    protected String summary;

    /**
     * Represents a treatment object that has a summary field. The constructor is the same, with the addition of the
     * summary parameter for the summary field.
     * @param summary A summary describing the treatment.
     * @see Treatment
     */
    public SummaryTreatment(String name, Impression impression, int priority, String status, String summary)
            throws DukeException {
        super(name, impression, priority, status);
        setSummary(summary);
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
     * replaced by the parameter's value. This method will also update the summary if it is supplied via
     * {@code editVals}.
     * @param newName The new name to assign to the treatment.
     * @param newPriority The new priority value to assign to the treatment.
     * @param editVals A map mapping member names to new values. If a value for "summary" is present, the treatment's
     *                 summary will be updated to that value.
     * @param isAppending If true, append all new data to the existing members. If false, overwrite the existing data.
     * @throws DukeException If a invalid value is supplied for any field.
     */
    @Override
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
