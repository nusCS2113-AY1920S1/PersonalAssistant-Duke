package task;

public class FixedDuration extends Task {
    protected String needs;

    /**
     * task.FixedDuration constructor
     * @param description task description
     * @param needs time needed to finish task
     */
    public FixedDuration(String description, String needs) {
        super(description);
        this.needs = needs;
    }

    @Override
    public String toString() {
        return "[F]" + super.toString() + " (needs: " + this.needs + ")";
    }

    @Override
    public String toWriteFile() {
        int boolToInt = isDone ? 1 : 0;
        return "F | " + boolToInt + " | " + this.description + " | " + this.needs + "\n";
    }
}

