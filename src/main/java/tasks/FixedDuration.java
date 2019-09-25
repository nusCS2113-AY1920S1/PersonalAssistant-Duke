package tasks;

public class FixedDuration extends Task {
    protected String needs;

    /**
     * The constructor initialise a FixedDuration Object.
     * @param description the description of the task within a fixed duration.
     * @param needs the time required to complete a task within a fixed duration.
     */
    public FixedDuration(String description, String needs) {
        super(description);
        this.needs = needs;
        super.type = "F";
    }

    @Override
    public String toString() {
        return "[F]" + super.toString() + "(needs: " + getNeeds() + ")";
    }

    public String getNeeds() {
        return needs;
    }

}

