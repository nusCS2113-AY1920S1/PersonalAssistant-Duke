package duke.tasks;

public class Fixed extends Task {
    private int hour;
    private int min;

    /**
     * Initializes a task to be done within a period of time with the given description.
     *
     * @param description A description of this task.
     */
    public Fixed(String description,int hour,int min) {
        super(description);
        this.hour = hour;
        this.min = min;
    }

    /**
     * Returns a string representation of this task.
     *
     * @return The desired string representation.
     */
    @Override
    public String toString() {
        String hour = (this.hour != 0 ? this.hour + " hours " : "");
        String min = (this.min != 0 ? this.min + " mins " : "");
        return "[F]" + super.toString() + " (needs " + hour + min + ")";
    }

    public String getFixed() {
        return hour + " | " + min;
    }
}
