package Task;

public class Deadline extends item{

    protected String by;

    public Deadline(String info, Boolean status, String by) {
        super(info, status);
        super.setType("D");
        super.setDate(by);
        this.by = by;
    }



    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
