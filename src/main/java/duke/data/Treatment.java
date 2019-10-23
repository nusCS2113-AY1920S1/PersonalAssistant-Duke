package duke.data;

import duke.exception.DukeException;

public abstract class Treatment extends DukeData {

    private int status;
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
    public int updatePriority(int priorityVal) throws DukeException {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String[] getStatusArr() {
        return statusArr;
    }

    public void setStatusArr(String[] statusArr) {
        this.statusArr = statusArr;
    }

    @Override
    public String toReportString() {
        // TODO: make this look better
        String toOutput = "\nTreatment given: " + getName() + "\nPriority level of treatment:  "
                + getPriority() + "\nStatus of treatment when rapport was made:  " + status
                + "\nDescription of the status tagged to this treatment: ";
        for (String status : statusArr) {
            toOutput += status + ", ";
        }
        toOutput = toOutput.substring(0, toOutput.length() - 2);
        return toOutput;
    }
}
