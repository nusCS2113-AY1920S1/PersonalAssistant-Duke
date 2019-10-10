package duke.modules;

import duke.util.TimeInterval;

public class FixedDurationTaskWithInterval_NotBeingUsed extends TaskWithInterval {

    public FixedDurationTaskWithInterval_NotBeingUsed(String task, TimeInterval interval) {
        super(task, interval);
    }

    public FixedDurationTaskWithInterval_NotBeingUsed(String task) {
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
