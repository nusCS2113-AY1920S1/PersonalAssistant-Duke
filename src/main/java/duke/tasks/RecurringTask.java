package duke.tasks;

import java.time.LocalDateTime;

public class RecurringTask extends TaskWithDates {
    private int repeatInterval;

    /**
     * Initializes a recurring task that repeats each set number of days.
     *
     * @param description A description of this task.
     */
    public RecurringTask(String description, LocalDateTime date, int repeatInterval) {
        super(description, date);
        this.repeatInterval = repeatInterval;
        updateRecurringTask();
    }

    @Override
    public String toString() {
        return ("[R]" + super.toString() + " by " + super.getStartDate().toString().replace("T", " ")
                + " (every " + repeatInterval + " days)");
    }

    /**
     * Update the date if necessary.
     */
    protected void updateRecurringTask() {
        while (LocalDateTime.now().isAfter(getStartDate())) {
            setStartDate(getStartDate().plusDays(repeatInterval));
        }
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }
}
