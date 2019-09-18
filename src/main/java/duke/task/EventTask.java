package duke.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class EventTask extends TimedTask {
    public EventTask(String name, LocalDateTime at) {
        super(name, at);
        type = 'E';
    }

    public EventTask(String name, LocalDateTime time, Reminder reminder) {
        super(name, time, reminder);
        type = 'E';
    }

    @Override
    public String toString() throws DateTimeException {
        return "[" + type + "]" + super.toString() + " (at: " + getTime() + ")";
    }
}
