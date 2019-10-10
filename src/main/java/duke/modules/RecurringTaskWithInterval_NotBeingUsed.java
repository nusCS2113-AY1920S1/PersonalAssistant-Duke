package duke.modules;

import duke.util.TimeInterval;

public class RecurringTaskWithInterval_NotBeingUsed extends TaskWithInterval {

    public RecurringTaskWithInterval_NotBeingUsed(String task, TimeInterval interval) {
        super(task, interval);
    }

    public RecurringTaskWithInterval_NotBeingUsed(String task) {
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
