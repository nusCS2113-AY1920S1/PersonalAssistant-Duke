package duke.data;

import duke.exception.DukeException;

public abstract class DukeData extends DukeObject {

    private Integer priority;
    protected String summary;

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
        super(impression.getName() + "-" + name, impression);
        this.priority = priority;
    }


    public void setName(String name) {
        super.setName(getParent().toString() + "\t" + name);
    }

    public Integer getPriority() {
        return priority;
    }

    /**
     * Updates priority of treatment.
     * @param priority The integer value of the priority between 1 to 4
     * @return the integer of the updated priority
     */
    public Integer setPriority(Integer priority) throws DukeException {
        this.priority = priority;
        return getPriority();
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Impression: " + getParent().toString() + "\n";
        informationString += "Priority: " + this.priority + "\n";
        return super.toString() + informationString;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
