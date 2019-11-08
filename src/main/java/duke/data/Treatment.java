package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.card.TreatmentCard;

public abstract class Treatment extends DukeData {

    private Integer statusIdx;

    /**
     * Abstraction of the actions taken to treat an impression the Doctor has about a patient.
     * A Treatment object corresponds to what actions the doctor is taking to treat the symptoms of a Patient,
     * the information that led to that particular treatment, the status of the treatment,
     * as well as an integer between 1-4 representing the priority or significance of the evidence.
     * Attributes:
     * - name: the treatment given
     * - impression: the impression object the treatment is tagged to
     * - status: the current status of the treatment
     * - priority: the priority level of the treatment
     */
    public Treatment(String name, Impression impression, int priority, int statusIdx) {
        super(name, impression, priority);
        this.statusIdx = statusIdx;
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
    public String toString() {
        String informationString;
        informationString = "Status: " + this.statusIdx + "\n";
        informationString += "Status Description: " + getStatusStr() + "\n";
        return super.toString() + informationString;
    }

    @Override
    public String toReportString() {
        String informationString;
        informationString = "Status of treatment: " + this.statusIdx + "\n";
        informationString += "Status Description: " + getStatusStr() + "\n";
        return informationString;
    }

    public void setStatusIdx(Integer statusIdx) {
        this.statusIdx = statusIdx;
    }

    public Integer getStatusIdx() {
        return statusIdx;
    }

    public abstract String getStatusStr();

    /**
     * Checks for equality with another Treatment object - all fields have the same value and all references point to
     * the same objects. Primarily for testing.
     * @param treatment The treatment to compare against.
     * @return True if all fields and references are the same, false otherwise.
     */
    public boolean equals(Treatment treatment) {
        if (super.equals(treatment)) {
            return statusIdx.equals(treatment.statusIdx);
        } else {
            return false;
        }
    }

    @Override
    public abstract TreatmentCard toCard() throws DukeFatalException;
}
