
package gazeeebo.tasks;

public class FixedDuration extends Task {
    /** Duration of the event.*/
    public String duration;

    /**
     * Constructor of FixedDuration.
     * @param description task description
     * @param duration duration of task
     */
    public FixedDuration(final String description, final String duration) {
        super(description);
        this.duration = duration;
    }

    /**
     * save this format into the txt file.
     * @return the format
     */
    @Override
    public String toString() {
        return "FD" + "|"
                + super.getStatusIcon() + "|"
                + super.description + "|" + duration;
    }

    /**
     * Add this format to the list.
     * @return the format
     */
    @Override
    public String listFormat() {
        return "[FD]" + "[" + super.getStatusIcon() + "] "
                + super.description + "(requires:" + duration + ")";
    }

}