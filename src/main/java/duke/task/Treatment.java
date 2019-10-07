package duke.task;

import duke.exception.DukeException;

public abstract class Treatment extends DukeData {

    int status;
    String[] statusArr;

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
            return super.setPriority(priorityVal);
        } else {
            throw new DukeException("The priority must be within 0 to 4!");
        }
    }

    /*
     * Updates status of the observation, i.e. the stage of completion
     * @param int the integer value of the status
     * @return int
     */
    public int updateStatus(int statusIdx) {
        this.status = statusIdx;
        return this.status;
    }
}
