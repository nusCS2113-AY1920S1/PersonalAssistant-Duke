package oof.task;

/**
 * Abstract parent class of all subclasses of Task.
 */
public abstract class Task {

    enum Frequency {
        ONCE,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY;
    }

    private String line;
    private boolean status;
    private Frequency frequency;
    private static final int DAILY = 1;
    private static final int WEEKLY = 2;
    private static final int MONTHLY = 3;
    private static final int YEARLY = 4;

    /**
     * Constructor for Task.
     *
     * @param line Command inputted by user.
     */
    public Task(String line) {
        this.line = line;
        this.status = false;
        this.frequency = Frequency.ONCE;
    }

    public void setStatus() {
        this.status = true;
    }

    public boolean getStatus() {
        return status;
    }

    public String getLine() {
        return line;
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
    public String getStatusIcon() {
        return (status ? "Y" : "N"); //return tick or X symbols
    }

    /**
     * Customises the toString() method to print the Task object.
     *
     * @return Customised String for Task object.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + line;
    }
}
