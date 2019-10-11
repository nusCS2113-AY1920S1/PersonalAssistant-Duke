package duke.modules;

import duke.util.DateTime;
import duke.util.TimeInterval;
import duke.util.TimePeriod;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public abstract class TaskWithPeriod extends Task {

    public TaskWithPeriod(String task) {
        super(task);
    }

    public abstract TimeInterval getInterval();

    public abstract LocalTime getBeginTime();

    public abstract LocalTime getEndTime();

    public abstract DateTime getBegin();

    public abstract DateTime getEnd();

    public abstract TimePeriod getPeriod();

    public abstract List<DayOfWeek> getDaysOfWeek();
}
