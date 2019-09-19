package duke.tasks;

import duke.DateTime;

import java.util.Date;

public class Tentative extends Task {
    private DateTime endDate;

    /**
     * Constructor for Duke.Tasks.Tentative object.
     * @param description name of the tentative event.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Tentative(String description, Date startDate, Date endDate) {
        super(description);
        this.startDate = new DateTime(startDate);
        this.endDate = new DateTime(endDate);
    }


    /**
     * This constructor is used for recreation of Duke.Tasks.Tentative from storage.
     * @param done  1 if task has been marked complete, 0 otherwise.
     * @param description the name or description of the tentative event.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Tentative(int done, String description, Date startDate, Date endDate) {
        super(description);
        this.isDone = (done == 1);
        this.startDate = new DateTime(startDate);
        this.endDate = new DateTime(endDate);
    }

    @Override
    public String storeString() {
        return "TE | " + super.storeString() + " | " + this.getStartDateString() + " | " + this.getEndDateString();
    }

    @Override
    public String toString() {
        return "[TE]" + super.toString() + " (around: "
                + this.getStartDateString() + " to " + this.getEndDateString()
                + " - date not fixed)";
    }

    private String getStartDateString() {
        return this.startDate.toString();
    }

    private String getEndDateString() {
        return this.endDate.toString();
    }

    public String getDescription() {
        return super.getTaskName();
    }
}
