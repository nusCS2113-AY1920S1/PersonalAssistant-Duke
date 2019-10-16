package spinbox.entities.items.tasks;

import spinbox.DateTime;

public class Deadline extends Schedulable {
    /**
     * Constructor for creation of SpinBox.Tasks.Deadline.
     * @param description the name or description of the deadline.
     * @param startDate the due date/time of the deadline.
     */
    public Deadline(String description, DateTime startDate) {
        super(description);
        this.startDate = startDate;
        taskType = TaskType.DEADLINE;
    }

    /**
     * This constructor is used for recreation of SpinBox.Tasks.Deadline from storage.
     * @param done 1 if task has been marked complete, 0 otherwise.
     * @param description the name or description of the deadline.
     * @param startDate the due date/time of the deadline.
     */
    public Deadline(int done, String description, DateTime startDate) {
        super(description);
        this.setDone(done == 1);
        this.startDate = startDate;
        taskType = TaskType.DEADLINE;
    }

    @Override
    public String storeString() {
        return "D | " + super.storeString() + " | " + this.getStartDateString();
    }

    @Override
    String getStartDateString() {
        return this.startDate.toString();
    }

    @Override
    String getEndDateString() {
        return null;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.startDate + ")";
    }

    /**
     * Check if given date is equal to deadline date.
     * @param inputDate the date to be compared.
     * @return true if equal, false if not equal.
     */
    @Override
    public boolean compareEquals(DateTime inputDate) {
        return (this.startDate.compareTo(inputDate) == 0);
    }
}
