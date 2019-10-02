package duke.tasks;

import duke.DateTime;

import java.util.Date;

public class Event extends Task {
    /**
     * Constructor for Duke.Tasks.Event object.
     * @param description name of the event.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Event(String description, Date startDate, Date endDate) {
        super(description);
        this.startDate = new DateTime(startDate);
        this.endDate = new DateTime(endDate);
    }

    /**
     * This constructor is used for recreation of Duke.Tasks.Deadline from storage.
     * @param done  1 if task has been marked complete, 0 otherwise.
     * @param description the name or description of the event.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Event(int done, String description, Date startDate, Date endDate) {
        super(description);
        this.isDone = (done == 1);
        this.startDate = new DateTime(startDate);
        this.endDate = new DateTime(endDate);
    }

    @Override
    public String storeString() {
        return "E | " + super.storeString() + " | " + this.getStartDateString() + " | " + this.getEndDateString();
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
        return "[E]" + super.toString() + " (at: " + this.getStartDateString() + " to " + this.getEndDateString() + ")";
    }

    /**
     * Check if date given is within event period.
     * @param inputDate the date to be compared.
     * @return true if within, false if not.
     */
    @Override
    public boolean compareEquals(DateTime inputDate) {
        boolean isAfterStartDate = (this.startDate.compareTo(inputDate) <= 0);
        boolean isBeforeEndDate = (this.endDate.compareTo(inputDate) >= 0);

        return (isAfterStartDate && isBeforeEndDate);
    }

    @Override
    public Boolean isOverlapping(Date startTime, Date endTime) {
        return startTime.before(endDate.getDateTime()) && startDate.getDateTime().before(endTime);
    }
}
