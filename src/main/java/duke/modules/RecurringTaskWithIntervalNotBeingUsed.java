package duke.modules;

import duke.util.TimeInterval;

public class RecurringTaskWithIntervalNotBeingUsed extends TaskWithInterval {

    public RecurringTaskWithIntervalNotBeingUsed(String task, TimeInterval interval) {
        super(task, interval);
    }

    public RecurringTaskWithIntervalNotBeingUsed(String task) {
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
