package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.card.EvidenceCard;

import java.util.Map;

/**
 * Abstraction of evidence supporting a medical diagnosis.
 * An Evidence object corresponds to the notes of the doctor
 * on a piece of medical evidence as well as
 * an integer between 1-4 representing the priority or significance of the evidence.
 * <p>
 * Attributes:
 * - name: information on the evidence
 * - impression: the impression object the evidence is tagged to
 * - priority: the priority level of the evidence
 * </p>
 */
public abstract class Evidence extends DukeData {

    protected String summary;

    public Evidence(String name, Impression impression, Integer priority, String summary) throws DukeException {
        super(name, impression, priority);
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public Integer setPriority(Integer priorityVal) throws DukeException {
        if (priorityVal >= 0 && priorityVal < 5) {
            super.setPriority(priorityVal);
            return super.getPriority();
        } else {
            throw new DukeException("The priority must be within 0 to 4!");
        }
    }

    @Override
    public abstract EvidenceCard toCard() throws DukeFatalException;

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
}
