package compal.tasks;

/**
 * Represents event task type with a starting date and time, as well as a fixed duration.
 * Input duration is in hours and minutes.
 */
public class FixedDurationTask extends Task {

    /**
     * Constructs FixedDurationTask object.
     *
     * @param description Description of fixed duration task.
     * @param date Starting date of fixed duration task.
     * @param time Starting time of fixed duration task.
     * @param hour Hour duration of fixed duration task.
     * @param minute Minute duration of fixed duration task.
     */
    public FixedDurationTask(String description, String date, String time, int hour, int minute) {
        super(description);
        super.symbol = "FDT";
        super.setDate(date);
        super.setTime(time);
        super.setDurationHour(hour);
        super.setDurationMinute(minute);
    }
}
