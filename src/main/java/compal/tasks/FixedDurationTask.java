package compal.tasks;

import java.util.Date;

public class FixedDurationTask extends Task {
    /**
     * Store the event type task. Event refers object with deadline such as,
     * meetings and what not.
     *
     * @param description Description of the event to be stored
     * @param date        of the event to be stored
     * @param hour        the duration hour
     * @param minute       the duration minute
     */
    public FixedDurationTask(String description, Date date, int hour, int minute) {
        super(description);
        super.symbol = "FDT";
        super.setDateTime(date);
        super.setHour(hour);
        super.setMinute(minute);
    }
}
