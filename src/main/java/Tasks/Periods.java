package Tasks;

public class Periods extends Task {

    protected String from, to;

    public Periods (String Description, String from, String to) {
        super(Description);
        this.from = from;
        this.to = to;
        super.type = "P";
    }

    @Override
    public String toString() {
        return "[P]" + super.toString() + "(from: " + from + " to " + to + ")";
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
}
