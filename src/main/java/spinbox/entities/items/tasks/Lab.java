package spinbox.entities.items.tasks;

import spinbox.DateTime;

public class Lab extends Schedulable {
    /**
     * Constructor for SpinBox.Tasks.Lab object.
     * @param description name of the lab.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Lab(String description, DateTime startDate, DateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
        taskType = TaskType.LAB;
    }

    /**
     * This constructor is used for recreation of SpinBox.Tasks.Lab from storage.
     * @param done  1 if task has been marked complete, 0 otherwise.
     * @param description the name or description of the lab.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     */
    public Lab(int done, String description, DateTime startDate, DateTime endDate) {
        super(description);
        this.updateDone(done == 1);
        this.startDate = startDate;
        this.endDate = endDate;
        taskType = TaskType.LAB;
    }

    @Override
    public String storeString() {
        return "LAB | " + super.storeString() + " | " + this.getStartDateString() + " | " + this.getEndDateString();
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
        return "[LAB]" + super.toString() + " (at: " + this.getStartDateString() + " to " + this.getEndDateString()
                + ")";
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
    public Boolean isOverlapping(DateTime startTime, DateTime endTime) {
        return startTime.before(endDate) && startDate.before(endTime);
    }
}