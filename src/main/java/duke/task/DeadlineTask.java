package duke.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class DeadlineTask extends TimedTask {

    public DeadlineTask(String _name, LocalDateTime _by) {
        super(_name, _by);
        type = 'D';
    }

    @Override
    public String toString() throws DateTimeException {
        return "[" + type + "]" + super.toString() + " (by: " + getTime() + ")";
    }
}
