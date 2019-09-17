package duke.tasks;

public class DoAfter extends Task {
    protected String after;

    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
    }

    @Override
    public String toString() {
        return ("[A]" + super.toString() + " (after " + this.after + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("A" + super.fileOutFormat() + "|" + this.after);
    }

}