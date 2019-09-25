package Model_Classes;

public class RecurringToDo extends ToDo {
    private String recurrence;
    /**
     * Constructor of the class. Takes in the description of the task
     *
     * @param description Description of the task.
     */
    public RecurringToDo(String description, String recurrence) {
        super(description);
        this.recurrence = recurrence;
    }

    @Override
    public String toString() {
        return super.toString() + " (R: " + recurrence + ")";
    }
}
