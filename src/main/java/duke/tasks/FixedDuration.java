package duke.tasks;

public class FixedDuration extends Task {
    protected String needs;

    public FixedDuration(String description, String needs) {
        super(description);
        this.needs = needs;
    }

    @Override
    public String toString() {
        return ("[F]" + super.toString() + " (needs " + this.needs + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("F" + super.fileOutFormat() + "|" + this.needs);
    }

}
