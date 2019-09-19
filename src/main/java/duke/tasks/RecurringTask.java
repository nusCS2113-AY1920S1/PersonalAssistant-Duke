package duke.tasks;

public class RecurringTask extends Task {
    private Integer period; //1 = day

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
        } catch (NumberFormatException e){
            System.out.println(e.getMessage() + " " + dateAndTime + getTask());
        }
    }

    @Override
    public String writingFile() { return "D" + "|" + super.writingFile() +
            "|" + period; }

    @Override
    public String toString() {
        return "[D]" + super.toString() +
                " (every: " + period + " days)";
    }
}
