package duke.task;

public class RecurringTask extends Task{
    private String day;
    public RecurringTask(String description,String day) {
        super(description);
        type = 'R';
        this.day = day;
    }

    /**
     * This method will return the string which day the recurring event is.
     * @return a string containing every and the day of the week that the recurring occur on.
     */
    @Override
    public String getDateStr() {
        return "(every: " + day + ")";
    }

    @Override
    public String formatDateSave() {
        return " | " + day;
    }
}
