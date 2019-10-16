package gazeeebo.Tasks;

public class DoAfter extends Task {
    public String before;
    public String after;

    public DoAfter(String description, String before, String after) {
        super(description);
        this.before = before;
        this.after = after;
    }

    @Override
    public String toString() {
        return "DA" + "|" + super.getStatusIcon() + "|" + after + "|" + before;
    }

    @Override
    public String listFormat() {
        return "[DA]" + "[" + super.getStatusIcon() + "] " + after + "(/after:" + before + ")";
    }

}
