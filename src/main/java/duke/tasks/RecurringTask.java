package duke.tasks;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class RecurringTask extends TaskWithDates {
    private LocalDateTime date;
    private int repeatInterval;

    /**
     * Initializes a recurring task that repeats each set number of days.
     *
     * @param description A description of this task.
     */
    public RecurringTask(String description, LocalDateTime date, int repeatInterval) {
        super(description, date);
        this.repeatInterval = repeatInterval;
        this.date = date;
        updateRecurringTask();
    }

    /**
     * Initializes a recurring task not yet done with the given description.
     *
     * @param description A description of this event.
     */
    public RecurringTask(String description, String date, int repeatInterval) {
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
    public void updateRecurringTask() {
        if (LocalDateTime.now().isAfter(this.getStartDate())) {
            this.startDate = this.startDate.plusDays(repeatInterval);
        }
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }
}
