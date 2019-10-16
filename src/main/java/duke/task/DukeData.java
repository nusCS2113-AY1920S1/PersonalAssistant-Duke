package duke.task;

import duke.exception.DukeException;

public abstract class DukeData extends DukeObject {

    private Impression impression;
    private int priority;

    /**
     * Abstraction of the evidence or treatment data of a patient.
     * A DukeData object corresponds to the evidence or treatment a doctor has,
     * the impression that led to that data as well as an integer
     * between 1-4 representing the priority or significance of the investigation.
     * Attributes:
     * - name: the evidence or treatment needed
     * - impression: the impression object the data is tagged to
     * - priority: the priority level of the investigation
     */
    public DukeData(String name, Impression impression, int priority) {
        super(impression.getName() + "\t" + name);
        this.impression = impression;
        this.priority = priority;
    }

    /*
     * This updatePriority function updates priority of treatment
     * @param int the integer value of the priority between 1 to 4
     * @return the integer of the updated priority
     */
    public abstract int updatePriority(int priorityVal) throws DukeException;

    public Impression getImpression() {
        return impression;
    }

    public void setImpression(Impression impression) {
        this.impression = impression;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
