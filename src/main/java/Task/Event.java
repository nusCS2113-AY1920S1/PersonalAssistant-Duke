package Task;

public class Event extends item{

    protected String by;

    public Event(String info, Boolean status, String by) {
        super(info, status);
        super.setType("E");
        super.setDate(by);
        this.by = by;
    }


    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + by + ")";
    }
}