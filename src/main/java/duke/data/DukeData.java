package duke.data;

import duke.exception.DukeException;

import java.util.Map;

public abstract class DukeData extends DukeObject {

    // TODO change priority to primitive int

    public static final int PRIORITY_MAX = 4;
    private Integer priority;
    protected String summary;
    private transient Impression parent;

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
    public DukeData(String name, Impression impression, Integer priority) throws DukeException {
        super(name, impression);
        parent = impression;
        setPriority(priority);
    }

    @Override
    public Impression getParent() {
        return parent;
    }

    public void setName(String name) {
        super.setName(name);
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
        if (priority < 0 || priority > DukeData.PRIORITY_MAX) {
            throw new DukeException("Priority must be between 0 and " + DukeData.PRIORITY_MAX + "!");
        }
        this.priority = priority;
        return getPriority();
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Impression: " + getParent().getName() + "\n";
        informationString += "Priority: " + this.priority + "\n";
        return super.toString() + informationString;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Checks for equality with another DukeData object - all fields have the same value and all references point to
     * the same objects. Primarily for testing.
     * @param other The DukeData to compare against.
     * @return True if all fields and references are the same, false otherwise.
     */
    public boolean equals(DukeData other) {
        return getName().equals(other.getName())
                && priority.equals(other.priority)
                && getParent() == other.getParent()
                && ((getSummary() == null && other.getSummary() == null) || getSummary().equals(other.getSummary()));
        // null check required because medicine summary is null
    }

    public void edit(String newName, int newPriority, String newSummary, Map<String, String> editVals,
                     boolean isAppending)
            throws DukeException {
        if (newName != null) {
            setName((isAppending) ? getName() + newName : newName);
        }
        if (newPriority != -1) {
            setPriority(newPriority);
        }
        if (newSummary != null) {
            setSummary((isAppending) ? getSummary() + newSummary : newSummary);
        }
    }
}
