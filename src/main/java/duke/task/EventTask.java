package duke.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class EventTask extends TimedTask {

    private LocalDateTime endTime;

    /**
     * Constructor for Event with Reminders.
     * @param name description of event
     * @param time start time of event
     * @param endTime end time of event
     * @param reminder reminder for event
     */
    public EventTask(String name, LocalDateTime time, LocalDateTime endTime, Reminder reminder) {
        super(name, time, reminder);
        this.endTime = endTime;
        type = 'E';
    }

    /**
     * Constructor for Events without Reminders.
     * @param name description of event
     * @param at start time of event
     * @param endTime end time of event
     */
    public EventTask(String name, LocalDateTime at, LocalDateTime endTime) {
        super(name, at);
        this.endTime = endTime;
        type = 'E';
    }

    String getEndTime() throws DateTimeException {
        return endTime.format(super.getPatDatetimeDisplay());
    }

    @Override
    public String toData() {
        return super.toData() + "\t" + endTime.format(getPatDatetime());
    }

    @Override
    public String toString() throws DateTimeException {
        if (type == 'E') {
            return "[" + type + "]" + super.toString() + " (at: " + getTime() + " - " + getEndTime() + ")";
        }
        return "[" + type + "]" + super.toString();
    }
}
