package gazeeebo.Tasks;

public class DoAfter extends Task {
    private String before;
    private String after;

    public DoAfter(final String description, final String before, final String after) {
        super(description);
        this.before = before;
        this.after = after;
    }

    /**
     * This method gives the format when save into the txt file.
     * @return the txt file format.
     */
    @Override
    public String toString() {
        return "DA" + "|" + super.getStatusIcon() + "|" + after + "|" + before;
    }

    /**
     * This method gives the format when the list is printed
     * @return the list format.
     */
    @Override
    public String listFormat() {
        return "[DA]" + "[" + super.getStatusIcon() + "] " + after + "(/after:" + before + ")";
    }

}
