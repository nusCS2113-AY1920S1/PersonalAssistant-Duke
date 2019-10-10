package duke.items.tasks;

import duke.DateTime;

import java.util.Date;

public class Within extends Task {
    /**
     * Constructor for Duke.Tasks.Within object.
     * @param description name of the task.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Within(String description, DateTime startDate, DateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * This constructor is used for recreation of Duke.Tasks.Within from storage.
     * @param done  1 if task has been marked complete, 0 otherwise.
     * @param description name of the task.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Within(int done, String description, DateTime startDate, DateTime endDate) {
        super(description);
        this.setDone(done == 1);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String storeString() {
        return "W | " + super.storeString() + " | " + this.getStartDateString() + " | " + this.getEndDateString();
    }

    @Override
    String getStartDateString() {
        return this.startDate.toString();
    }

    @Override
    String getEndDateString() {
        return this.endDate.toString();
    }

    @Override
    public String toString() {
        return "[W]" + super.toString() + " (between: " + this.getStartDateString()
                + " and " + this.getEndDateString() + ")";
    }
}
