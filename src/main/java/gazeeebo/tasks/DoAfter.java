package gazeeebo.tasks;

public class DoAfter extends Task {
    /** task to do before... */
    public String before;
    /** task to do after ...*/
    public String after;


    /**
     * constructor of DoAfter.
     * @param description task description
     * @param before before task
     * @param after after task
     */
    public DoAfter(final String description,
                   final String before, final String after) {
        super(description);
        this.before = before;
        this.after = after;
    }

    /**
     * Save this format in the txt file.
     * @return the format
     */
    @Override
    public String toString() {
        return "DA" + "|" + super.getStatusIcon()
                + "|" + after + "|" + before;
    }

    @Override
    public String listFormat() {
        return "[DA]" + "[" + super.getStatusIcon() + "] "
                + after + "(/after:" + before + ")";
    }

}
