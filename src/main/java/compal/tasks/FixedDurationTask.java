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
     * @param date        Starting date of fixed duration task.
     * @param sTime        Starting time of fixed duration task.
     * @param eTime       End time
     */
    public FixedDurationTask(String description, Priority priority, String date, String sTime,String eTime){
        super(description, priority);
        super.symbol = "FDT";
        super.setDate(date);
        super.setStartTime(sTime);
        super.setEndTime(eTime);
    }
}
