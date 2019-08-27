public class Deadline extends Task {

    //deadline do homework /by no idea :-p
    //[D][✗] do homework (by: no idea :-p)
    //Deadline reads in an input, separate via a /by
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
