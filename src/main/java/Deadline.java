public class Deadline extends Task {

    //deadline do homework /by no idea :-p
    //[D][✗] do homework (by: no idea :-p)
    //Deadline reads in an input, separate via a /by
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getType() {
        return "[D]";
    }

    public String getBy() {
        return this.by;
    }

    public String getStatusIcon() {
        return "[D]" + "[" + (isDone ? "\u2713" : "\u2718") + "] " + this.description + " (by: " + by + ")";
    }
}
