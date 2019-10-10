package duke.modules;

import duke.util.TimeInterval;

public class FixedDurationTaskWithIntervalNotBeingUsed extends TaskWithInterval {

    public FixedDurationTaskWithIntervalNotBeingUsed(String task, TimeInterval interval) {
        super(task, interval);
    }

    public FixedDurationTaskWithIntervalNotBeingUsed(String task) {
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
