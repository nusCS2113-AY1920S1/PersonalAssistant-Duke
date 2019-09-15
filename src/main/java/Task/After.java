package Task;

public class After extends item {
    protected String after;

    public After(String info, Boolean status, String after) {
        super(info, status);
        super.setType("A");
        super.setDate(after);
        this.after = after;
    }



    @Override
    public String toString() {
        return "[A]" + super.toString() + " (after: " + after + ")";
    }
}
