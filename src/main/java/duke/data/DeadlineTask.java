package duke.data;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class DeadlineTask extends TimedTask {
    public DeadlineTask(String name, LocalDateTime by) {
        super(name, by);
        type = 'D';
    }

    public DeadlineTask(String name, LocalDateTime time, Reminder reminder) {
        super(name, time, reminder);
        type = 'D';
    }

    @Override
    public String toString() throws DateTimeException {
        return "[" + type + "]" + super.toString() + " (by: " + getTime() + ")";
    }
}
