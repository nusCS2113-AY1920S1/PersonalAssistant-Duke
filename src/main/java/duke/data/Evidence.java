package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.card.EvidenceCard;

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

    public Evidence(String name, Impression impression, Integer priority, String summary) throws DukeException {
        super(name, impression, priority);
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
}
