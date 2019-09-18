package task;

import parser.TimeParser;

/**
 * Represents a specified task by extending the <code>Task</code> class
 * without adding any extra information.
 */
public class ToDo extends Task {
    private long timeTaken;

    /**
     * Constructs the task with description.
     *
     * @param description Description of task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Constructs a <code>ToDo</code> object from the separated storage string.
     *
     * @param splitStorageStrings the separated storage string.
     */
    public ToDo(String[] splitStorageStrings) {
        super(splitStorageStrings);
        this.recurringType = splitStorageStrings[5];
        timeTaken = Long.parseLong(splitStorageStrings[4]);
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = TimeParser.parseDuration(timeTaken);
    }

    /**
     * Overrides the <code>toString()</code> method in parent class <code>Task</code>,
     * and returns information of the task to be printed by UI.
     * e.g. "[T][✓] attend the party"
     *
     * @return Information of the task to be printed by UI.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString() + " (needs: " + TimeParser.formatDuration(timeTaken) + ")"
                + "(" + recurringType + ")";
    }

    /**
     * Returns information of the task in storage format.
     * e.g. "T | 1 | attend the party"
     *
     * @return Information of the task to be stored in storage.
     */
    @Override
    public String toStorageString() {
        return "T | " + super.toStorageString() + " | " + timeTaken
                + " | " + recurringType;
    }
}
