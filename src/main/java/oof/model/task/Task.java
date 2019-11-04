package oof.model.task;

/**
 * Abstract parent class of all subclasses of Task.
 */
public abstract class Task {

    enum Frequency {
        ONCE,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

    static final String DELIMITER = "||";
    static final int INDEX_DATE = 0;
    static final int INDEX_TIME = 1;
    String description;
    private boolean status;
    private Frequency frequency;
    private static final int DAILY = 1;
    private static final int WEEKLY = 2;
    private static final int MONTHLY = 3;
    private static final int YEARLY = 4;

    /**
     * Constructor for Task.
     *
     * @param description Description of task.
     */
    public Task(String description) {
        this.description = description;
        this.status = false;
        this.frequency = Frequency.ONCE;
    }

    public void setStatus() {
        this.status = true;
    }

    public boolean getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Sets the frequency of the recurrence of a task.
     * @param frequency Frequency of a recurrence in the form of integer from user input.
     */
    public void setFrequency(int frequency) {
        if (frequency == DAILY) {
            this.frequency = Frequency.DAILY;
        } else if (frequency == WEEKLY) {
            this.frequency = Frequency.WEEKLY;
        } else if (frequency == MONTHLY) {
            this.frequency = Frequency.MONTHLY;
        } else if (frequency == YEARLY) {
            this.frequency = Frequency.YEARLY;
        }
    }

    /**
     * Retrieves the status icon of the Task.
     *
     * @return Status icon of the Task.
     */
    String getStatusIcon() {
        return (status ? "Y" : "N"); //return tick or X symbols
    }

    /**
     * Converts a task object to string format for storage.
     * @return Task object in string format for storage.
     */
    public abstract String toStorageString();

    /**
     * Customises the toString() method to print the Task object.
     *
     * @return Customised String for Task object.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
