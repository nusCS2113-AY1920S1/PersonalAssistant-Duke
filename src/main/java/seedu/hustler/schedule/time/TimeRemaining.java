package seedu.hustler.schedule.time;

import java.time.Duration;
import java.time.LocalDateTime;
import seedu.hustler.task.Event;
import seedu.hustler.task.Deadline;
import seedu.hustler.task.ToDo;
import seedu.hustler.task.Task;

/**
 * Class that gives the time remaining
 * for a task to be due.
 */
public class TimeRemaining implements Timing {

    /**
     * Computes amount of time available before task is due.
     *
     * @param task task to be checked.
     * @return amount of time available (14 days in case of todo)
     */
    public Duration secondsLeft(Task task) {
        if (task instanceof ToDo) {
            return Duration.ofDays(14);
        } else {
            return Duration.between(LocalDateTime.now(), task.getDateTime());
        }

    }

}
