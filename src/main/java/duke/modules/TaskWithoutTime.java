package duke.modules;

import duke.exceptions.ModInvalidTimePeriodException;
import java.time.LocalDateTime;

public class TaskWithoutTime extends Task {

    public TaskWithoutTime(String task) throws ModInvalidTimePeriodException {
        super(task);
    }

    @Override
    public LocalDateTime getTime() {
        return null;
    }
}
