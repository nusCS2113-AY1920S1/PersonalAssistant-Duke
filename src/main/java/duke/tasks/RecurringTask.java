package duke.tasks;

public class RecurringTask extends Task {

    // Each period is 1 day.
    private Integer period;

    /**
     * Constructor for recurring tasks.
     * @param input User input for task.
     */
    public RecurringTask(String... input) {
        super(input[0]);
        setRecurring(input[input.length - 1]);
    }

    /**
     * Specific to recurring task, the recurring time data has to be stored.
     * @param dateAndTime String date and time associated with the task.
     */
    private void setRecurring(String dateAndTime) {
        try {
            period = Integer.parseInt(dateAndTime);
            System.out.println(period);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage() + " " + dateAndTime + getTask());
        }
    }

    @Override
    public String writingFile() {
        return "R"
                + "|"
                + super.writingFile()
                + "|"
                + period;
    }

    @Override
    public String toString() {
        return "[R]"
                + super.toString()
                + " (every: "
                + period
                + " days)";
    }
}
