package planner.modules;

import planner.exceptions.ModInvalidTimeException;
import java.time.Duration;

public class RecurringTask extends TaskWithInterval {

    public RecurringTask(String task, String days, String hours, String minutes, String seconds)
            throws ModInvalidTimeException {
        super(task, parseInterval(days, hours, minutes, seconds));
    }

    public RecurringTask(String task, String durationString) {
        super(task, Duration.parse(durationString));
    }

    public RecurringTask(String task) {
        super(task);
    }

    @Override
    public String writingFile() {
        return "R"
                + "|"
                + super.writingFile()
                + "|"
                + this.getInterval().toString();
    }

    @Override
    public String toString() {
        return "[R]"
                + super.toString()
                + " (every: "
                + this.getInterval().toReadableString()
                + ")";
    }
}
