package tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Events extends Task {

    protected Date startAt;
    protected Date endAt;
    private SimpleDateFormat simpleDateFormat;

    /**
     * The constructor to initialize a Events object.
     * @param description The description of the event.
     * @param start The start date of the event
     * @param end The end date of the event.
     */
    public Events(String description, Date start, Date end) {
        super(description);
        this.startAt = start;
        this.endAt = end;
        super.type = "E";
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + getStartAt() + " to " + getEndAt() + ")";
    }


    public String getStartAt() {
        return simpleDateFormat.format(startAt);
    }

    public String getEndAt() {
        return simpleDateFormat.format(endAt);
    }

    public Date getStartDateAt() {
        return startAt;
    }

    public Date getEndDateAt() {
        return endAt;
    }
}