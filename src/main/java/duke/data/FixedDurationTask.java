package duke.data;

import java.time.Duration;

public class FixedDurationTask extends ToDoTask {

    private Duration period;

    /**
     * Constructor for FixedDurationTask.
     * @param name task
     * @param period duration task takes
     */
    public FixedDurationTask(String name, Duration period) {
        super(name);
        this.period = period;
        type = 'F';
    }

    @Override
    public String toString() {
        long seconds = period.getSeconds();
        String prettyPeriod = String.format("%d hour, %02d minutes, %02d seconds",
                                            seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
        return super.toString() + " (for: " + prettyPeriod + ")";
    }
}
