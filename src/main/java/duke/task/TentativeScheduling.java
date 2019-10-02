package duke.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TentativeScheduling extends Task {
    protected String on;
    protected Date date;
    protected SimpleDateFormat dateFormatter;

    /**
     * Constructor for class TentativeScheduling.
     * @param description String containing the description of the task
     * @param on String containing multiple dates and time
     */
    public TentativeScheduling(String description, String on) throws ParseException {
        super(description);
        this.on = on;
        taskType = TaskType.TENTATIVESCHEDULING;
        date = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(on);
        dateFormatter = new SimpleDateFormat("d MMMM yyyy, hh:mm a");
    }

    @Override
    public String toSaveString() {
        return "TS" + super.toSaveString() + " | " + on;
    }

    @Override
    public String toString() {
        return "[TS]" + super.toString() + " (on: " + dateFormatter.format(date) + ")";
    }

    public Date getDateTime() {
        return null;
    }
}
