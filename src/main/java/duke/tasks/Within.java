package duke.tasks;

import duke.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Within extends Task {
    private DateTime endDate;

    /**
     * Constructor for Duke.Tasks.Within object.
     * @param description name of the task.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Within(String description, Date startDate, Date endDate) {
        super(description);
        this.startDate = new DateTime(startDate);
        this.endDate = new DateTime(endDate);
    }

    /**
     * This constructor is used for recreation of Duke.Tasks.Within from storage.
     * @param done  1 if task has been marked complete, 0 otherwise.
     * @param description name of the task.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Within(int done, String description, Date startDate, Date endDate) {
        super(description);
        this.isDone = (done == 1);
        this.startDate = new DateTime(startDate);
        this.endDate = new DateTime(endDate);
    }

    @Override
    public String storeString() {
        return "W | " + super.storeString() + " | " + this.getStartDateString() + " | " + this.getEndDateString();
    }

    @Override
    public String toString() {
        return "[W]" + super.toString() + " (between: " + this.getStartDateString()
                + " and " + this.getEndDateString() + ")";
    }

    private String getStartDateString() {
        return this.startDate.toString();
    }

    private String getEndDateString() {
        return this.endDate.toString();
    }
}
