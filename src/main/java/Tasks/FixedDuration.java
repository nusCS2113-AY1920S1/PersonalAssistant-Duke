package Tasks;

public class FixedDuration extends Task {
    protected String needs;

    public FixedDuration(String description, String needs) {
        super(description);
        this .needs = needs;
        super.type = "F";
    }

    @Override
    public String toString() { return "[F]" + super.toString() + "(needs: " + getNeeds() + ")"; }

    public String getNeeds() { return needs;}

}

