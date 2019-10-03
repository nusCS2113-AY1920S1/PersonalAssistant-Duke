package duke.tasks;

public class BetweenTask extends Task {
    private String startDate;
    private String endDate;

    /**
     * Constructor for a duke.tasks.BetweenTask task, which consists of the description of the task and the period
     * associated with it.
     *
     * @param description refers to the description of the task
     * @param startDate refers to the start of the period
     * @param endDate refers to the end of the period
     */
    public BetweenTask(String description, String startDate, String endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns a String representation of the duke.tasks.BetweenTask object, displaying its type
     * (duke.tasks.BetweenTask), description and the period associated with it.
     *
     * @return a String representation of the duke.tasks.BetweenTask object
     */
    @Override
    public String toString() {
        return "[B]" + super.toString() + " (between: " + startDate + " and " + endDate + ")";
    }

}
