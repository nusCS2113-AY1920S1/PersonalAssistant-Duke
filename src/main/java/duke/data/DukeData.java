package duke.data;

import duke.exception.DukeException;

public abstract class DukeData extends DukeObject {

    private String impression;
    private Integer priority;

    /**
     * Abstraction of the evidence or treatment data of a patient.
     * A DukeData object corresponds to the evidence or treatment a doctor has,
     * the impression that led to that data as well as an integer
     * between 1-4 representing the priority or significance of the investigation.
     * Attributes:
     * @param name the evidence or treatment needed
     * @param impression the impression object the data is tagged to
     * @param priority the priority level of the investigation
     */
    public DukeData(String name, Impression impression, Integer priority) {
        super(impression.getName() + "\t" + name);
        this.impression = impression.getName();
        this.priority = priority;
    }

    /**
     * Abstraction of the evidence or treatment data of a patient.
     * A DukeData object corresponds to the evidence or treatment a doctor has,
     * the impression that led to that data as well as an integer
     * between 1-4 representing the priority or significance of the investigation.
     * Attributes:
     * @param name the evidence or treatment needed
     * @param impression the name of the impression object the data is tagged to
     * @param priority the priority level of the investigation
     */
    protected DukeData(String name, String impression, Integer priority) {
        super(impression + "\t" + name);
        this.impression = impression;
        this.priority = priority;
    }

    /*
     * This updatePriority function updates priority of treatment
     * @param int the integer value of the priority between 1 to 4
     * @return the integer of the updated priority
     */
    public abstract Integer updatePriority(Integer priorityVal) throws DukeException;

    public String getImpression() {
        return impression;
    }

    /**
     * This function sets the impression and updates the name of the DukeData object.
     * @param impression the new impression used
     */
    public void setImpression(Impression impression) {
        this.impression = impression.getName();
        String[] bits = this.getName().split("\t");
        String actualName = bits[bits.length - 1];
        setName(actualName);
    }

    public void setName(String name) {
        super.setName(this.impression + "\t" + name);
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
