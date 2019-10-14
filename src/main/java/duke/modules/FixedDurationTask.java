package duke.modules;

import duke.exceptions.ModInvalidTimeException;
import java.time.Duration;

public class FixedDurationTask extends TaskWithInterval {

    public FixedDurationTask(String task, String days, String hours, String minutes, String seconds)
            throws ModInvalidTimeException {
        super(task, TaskWithInterval.parseInterval(days, hours, minutes, seconds));
    }

    public FixedDurationTask(String task, String durationString) {
        super(task, Duration.parse(durationString));
    }

    public FixedDurationTask(String task) {
        super(task);
    }

    @Override
    public String writingFile() {
        return "F"
                + "|"
                + super.writingFile()
                + "|"
                + this.getInterval().toString();
    }

    @Override
    public String toString() {
        return "[F]"
                + super.toString()
                + " (needs: "
                + this.getInterval().toReadableString()
                + ")";
    }
}
