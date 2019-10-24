package duke.data;

import duke.exception.DukeException;

public abstract class Treatment extends DukeData {

    private Integer status;
    private String[] statusArr;

    /**
     * Abstraction of the actions taken to treat an impression the Doctor has about a patient.
     * A Treatment object corresponds to what actions the doctor is taking to treat the symptoms of a Patient,
     * the information that led to that particular treatment, the status of the treatment,
     * as well as an integer between 1-4 representing the priority or significance of the evidence.
     * Attributes:
     * - name: the treatment given
     * - impression: the impression object the treatment is tagged to
     * - status: the current status of the treatment
     * - statusArr: description of the status tagged to this treatment
     * - priority: the priority level of the treatment
     */
    public Treatment(String name, Impression impression, int priority, int status, String[] statusArr) {
        super(name, impression, priority);
        this.status = status;
        this.statusArr = statusArr;
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

    /*
     * Updates status of the observation, i.e. the stage of completion
     * @param int the integer value of the status
     */
    public void updateStatus(int statusIdx, String[] statusArr) {
        setStatus(statusIdx);
        setStatusArr(statusArr);
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Status: " + Integer.toString(this.status) + "\n";
        String joinedStatus = String.join(" ", this.statusArr);
        informationString += "Status Description: " + joinedStatus + "\n";
        return super.toString() + informationString;
    }

    @Override
    public String toReportString() {
        String informationString;
        informationString = "Status of treatment: " + Integer.toString(this.status) + "\n";
        String joinedStatus = String.join(" ", this.statusArr);
        informationString += "Status Description: " + joinedStatus + "\n";
        return informationString;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public String[] getStatusArr() {
        return statusArr;
    }

    public void setStatusArr(String[] statusArr) {
        this.statusArr = statusArr;
    }

}
