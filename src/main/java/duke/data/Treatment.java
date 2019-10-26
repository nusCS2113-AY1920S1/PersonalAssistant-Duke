package duke.data;

import duke.exception.DukeException;

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
    public Integer updatePriority(Integer priorityVal) throws DukeException {
        if (priorityVal >= 0 && priorityVal < 5) {
            super.setPriority(priorityVal);
            return super.getPriority();
        } else {
            throw new DukeException("The priority must be within 0 to 4!");
        }
    }

    /**
     * Updates status of the observation, i.e. the stage of completion
     * @param statusIdx the integer value of the status
     */
    public void updateStatus(int statusIdx) {
        setStatusIdx(statusIdx);
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Status: " + Integer.toString(this.statusIdx) + "\n";
        informationString += "Status Description: " + getStatusStr() + "\n";
        return super.toString() + informationString;
    }

    @Override
    public String toReportString() {
        String informationString;
        informationString = "Status of treatment: " + Integer.toString(this.statusIdx) + "\n";
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

}
