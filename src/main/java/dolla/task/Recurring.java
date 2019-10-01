package dolla.task;

public class Recurring extends Task {
    private String day;

    /**
     * This method will set the type of a recurring task to R and set the description and day in this class
     * as the input parameters.
     * @param description is the description of the task
     * @param day is the day of the week that the user want the recurring to happen.
     */
    public Recurring(String description, String day) {
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

    /**
     * This method will a return a string in save format.
     * @return a string containg the save format.
     */
    @Override
    public String formatDateSave() {
        return " | " + day;
    }
}
